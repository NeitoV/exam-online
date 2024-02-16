package com.football.pitch.service;

import com.football.pitch.data.dto.PaginationDTO;
import com.football.pitch.data.dto.booking.BookingCreationDTO;
import com.football.pitch.data.dto.MessageResponse;
import com.football.pitch.data.dto.booking.BookingShowDTO;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

public interface BookingService {
    @Transactional
    MessageResponse createBooking(BookingCreationDTO bookingCreationDTO);

    MessageResponse cancelBooking(long bookingId);

    @PostConstruct
    void doneBooking();

    PaginationDTO filterBooking(Date date, Long typeId, Long durationId,
                                Long statusId, int pageNumber, int pageSize);

    BookingShowDTO findById(long id);

    List<BookingShowDTO> findHistoryBooking();
}
