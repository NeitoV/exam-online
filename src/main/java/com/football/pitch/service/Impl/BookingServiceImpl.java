package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.PaginationDTO;
import com.football.pitch.data.dto.booking.BookingCreationDTO;
import com.football.pitch.data.dto.MessageResponse;
import com.football.pitch.data.dto.booking.BookingShowDTO;
import com.football.pitch.data.enity.*;
import com.football.pitch.data.mapper.BookingMapper;
import com.football.pitch.data.repository.*;
import com.football.pitch.enumeration.ERole;
import com.football.pitch.enumeration.EStatus;
import com.football.pitch.exception.*;
import com.football.pitch.service.BookingService;
import com.football.pitch.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PitchDetailsRepository pitchDetailsRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private RevenueRepository revenueRepository;
    public static final int beliefMin = 80;
    public static final int penalty = 10;
    public static final int bonus = 5;

    public MessageResponse createBooking(BookingCreationDTO bookingCreationDTO) {
        Promotion promotion = null;
        String promotionCode = bookingCreationDTO.getPromotionCode();

        if (promotionCode != null) {
            promotion = promotionRepository.findByName(promotionCode).orElseThrow(
                    () -> new ResourceNotFoundException(Collections.singletonMap("promotion code: ", promotionCode))
            );

            Date promotionDateWithoutTime = removeTime(promotion.getDate());
            Date bookingCreationDateWithoutTime = removeTime(bookingCreationDTO.getDate());

            if (promotionDateWithoutTime.before(bookingCreationDateWithoutTime) || promotion.isDeleted())
                throw new InternalServerErrorException("Expired promotion code");
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AccessDeniedException(Collections.singletonMap("email: ", email))
        );

        Customer customer = customerRepository.findByUserId(user.getId()).orElse(null);

        if (customer.getBelief() < beliefMin) {
            throw new InternalServerErrorException("You have canceled booking schedule too many");
        }

        if (bookingCreationDTO.getDate() != null) {
            if (bookingRepository.existsByPitchDetailsIdAndDateAndStatusId(bookingCreationDTO.getPitchDetailsId(),
                    bookingCreationDTO.getDate(), EStatus.created)) {

                throw new ConflictException(Collections.singletonMap("booking date: ", bookingCreationDTO.getDate()));
            }
        } else throw new InternalServerErrorException("Date can't be null");

        Status status = statusRepository.findById(EStatus.created).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("status: ", EStatus.CREATED.toString()))
        );

        Booking booking = bookingMapper.toEntity(bookingCreationDTO);

        booking.setCustomer(customer);
        booking.setStatus(status);
        booking.setPromotion(promotion);
        booking.setTotalRevenue(totalRevenue(bookingCreationDTO.getPitchDetailsId(), promotion));

        bookingRepository.save(booking);


        return new MessageResponse(HttpServletResponse.SC_CREATED, "successfully");
    }

    private Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    private MessageResponse updateStatusBooking(long bookingId, long statusId, long customerId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("booking id: ", bookingId))
        );

        if (booking.getCustomer().getId() != customerId) {
            throw new AccessDeniedException(
                    Collections.singletonMap("You can't cancel someone else's booking schedule", null));
        }

        Status status = statusRepository.findById(statusId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("status id: ", statusId))
        );

        booking.setStatus(status);
        bookingRepository.save(booking);

        return new MessageResponse(HttpServletResponse.SC_OK, "successfully");
    }


    public MessageResponse cancelBooking(long bookingId) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AccessDeniedException(Collections.singletonMap("email: ", email))
        );


        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("booking id: ", bookingId))
        );

        if (user.getRole().getId() == ERole.roleCustomer) {

            if (booking.getCustomer().getUser().getId() != user.getId()) {
                throw new AccessDeniedException(Collections.singletonMap("Don't have permission", null));
            }
        }

        Customer customer = booking.getCustomer();

        int belief = customer.getBelief();

        customer.setBelief(belief - penalty);
        customerRepository.save(customer);

        return updateStatusBooking(bookingId, EStatus.canceled, customer.getId());
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void setBookingStatusDone() {
        Date date = new Date();
        List<Booking> bookingList = bookingRepository.findAllByDateBeforeAndStatusId(date, EStatus.created);

        for (Booking booking : bookingList) {
            Status status = statusRepository.findById(EStatus.done).orElseThrow(null);
            booking.setStatus(status);

            booking.getCustomer().setBelief(booking.getCustomer().getBelief() + bonus);
            bookingRepository.save(booking);

            Revenue revenue = new Revenue();
            Manager manager = booking.getPitchDetails().getPitch().getManager();
            Type type = booking.getPitchDetails().getPitch().getType();
            Revenue existsRevenue = revenueRepository.findByDateAndManagerIdAndTypeId(booking.getDate(),
                    manager.getId(), type.getId()).orElse(null);

            if (existsRevenue == null) {
                revenue.setDate(booking.getDate());
                revenue.setManager(manager);
                revenue.setDailyRevenue(booking.getTotalRevenue());
                revenue.setType(type);

            } else {
                revenue = existsRevenue;
                BigDecimal total = revenue.getDailyRevenue().add(booking.getTotalRevenue());
                revenue.setDailyRevenue(total);
            }

            revenueRepository.save(revenue);
        }
    }

    @Override
    @PostConstruct
    public void doneBooking() {
        setBookingStatusDone();
    }

    @Override
    public PaginationDTO filterBooking(Date date, Long typeId, Long durationId,
                                       Long statusId, int pageNumber, int pageSize) {
        Manager manager = managerService.getManagerFromToken();
        Long managerId = null;

        if (manager != null) {
            managerId = manager.getId();
        }

        Page<BookingShowDTO> page = bookingRepository.filterBooking(date, typeId, durationId, managerId, statusId,
                PageRequest.of(pageNumber, pageSize)).map(booking -> bookingMapper.toDTOShow(booking));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public BookingShowDTO findById(long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("booking id: ", id))
        );

        Manager manager = managerService.getManagerFromToken();
        if (manager != null) {
            if (manager.getId() != booking.getPitchDetails().getPitch().getManager().getId()) {
                throw new AccessDeniedException(Collections.singletonMap("You don't have permission", null));
            }
        }

        BookingShowDTO bookingShowDTO = bookingMapper.toDTOShow(booking);


        return bookingShowDTO;
    }

    @Override
    public List<BookingShowDTO> findHistoryBooking() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AccessDeniedException(Collections.singletonMap("email: ", email))
        );

        Customer customer = customerRepository.findByUserId(user.getId()).orElseThrow(
                () -> new AccessDeniedException(Collections.singletonMap("user has been delete", user.getId()))
        );

        List<BookingShowDTO> bookingList = bookingRepository.findAllByCustomerId(customer.getId()).stream()
                .map(booking -> bookingMapper.toDTOShow(booking)).collect(Collectors.toList());

        return bookingList;
    }


    private BigDecimal totalRevenue(long pitchDetailsId, Promotion promotion) {
        PitchDetails pitchDetails = pitchDetailsRepository.findById(pitchDetailsId).orElse(null);

        BigDecimal total = pitchDetails.getPrice().getPricePerHour().multiply(BigDecimal.valueOf(1.5));

        if (promotion != null) {

            BigDecimal discount = total.multiply(BigDecimal.valueOf(promotion.getDiscountPercentage()));

            if (discount.compareTo(promotion.getMaxGet()) > 0) {
                discount = promotion.getMaxGet();
            }

            total = total.subtract(discount);
        }

        return total;
    }


}
