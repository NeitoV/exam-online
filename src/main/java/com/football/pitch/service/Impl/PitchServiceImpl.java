package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.MessageResponse;
import com.football.pitch.data.dto.PaginationDTO;
import com.football.pitch.data.dto.pitch.PitchShowDTO;
import com.football.pitch.data.dto.pitch.PitchCreationDTO;
import com.football.pitch.data.dto.pitch.PitchDetailsShowDTO;
import com.football.pitch.data.enity.*;
import com.football.pitch.data.mapper.PitchDetailsMapper;
import com.football.pitch.data.mapper.PitchMapper;
import com.football.pitch.data.repository.*;
import com.football.pitch.enumeration.EStatus;
import com.football.pitch.exception.AccessDeniedException;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.service.ManagerService;
import com.football.pitch.service.PitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class PitchServiceImpl implements PitchService {
    @Autowired
    private PitchRepository pitchRepository;
    @Autowired
    private PitchMapper pitchMapper;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private PitchDetailsRepository pitchDetailsRepository;
    @Autowired
    private DurationRepository durationRepository;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private PitchDetailsMapper pitchDetailsMapper;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ManagerService managerService;

//    @Override
//    public MessageResponse createPitches(PitchCreationDTO pitchCreationDTO) {
//        Manager manager = managerRepository.findById(pitchCreationDTO.getManagerId()).orElseThrow(
//                () -> new ResourceNotFoundException(
//                        Collections.singletonMap("manager id: ", pitchCreationDTO.getManagerId()))
//        );
//
//        pitchCreationDTO.getPitchDTOS().forEach(pitchDTO -> {
//
//            Pitch pitch = pitchMapper.toEntity(pitchDTO);
//            pitch.setManager(manager);
//
//            createListPitchDetails(pitchRepository.save(pitch));
//        });
//
//        manager.setTotalPitch(pitchCreationDTO.getPitchDTOS().size());
//        managerRepository.save(manager);
//
//        return new MessageResponse(HttpServletResponse.SC_CREATED, "successfully");
//    }


    private void createListPitchDetails(Pitch pitch) {
        List<Duration> durations = durationRepository.findAll();

        durations.forEach(duration -> {
            PitchDetails pitchDetails = new PitchDetails();

            pitchDetails.setPitch(pitch);
            pitchDetails.setDuration(duration);

            Price price = priceRepository.findByPeriodIdAndTypeId
                    (duration.getPeriod().getId(), pitch.getType().getId()).orElse(null);

            pitchDetails.setPrice(price);

            pitchDetailsRepository.save(pitchDetails);

        });
    }


    @Override
    public MessageResponse createPitch(PitchCreationDTO pitchCreationDTO) {

        Manager manager = managerService.getManagerFromToken();

        if (manager == null) {

            manager = managerRepository.findById(pitchCreationDTO.getManagerId()).orElseThrow(
                    () -> new ResourceNotFoundException(
                            Collections.singletonMap("manager id: ", pitchCreationDTO.getManagerId()))
            );
        } else {

            if (manager.getId() != pitchCreationDTO.getManagerId()) {
                throw new AccessDeniedException(Collections.singletonMap("You don't have permission", null));
            }
        }

        Pitch pitch = pitchMapper.toEntity(pitchCreationDTO.getPitchDTO());
        pitch.setManager(manager);

        createListPitchDetails(pitchRepository.save(pitch));


        manager.setTotalPitch(manager.getTotalPitch() + 1);
        managerRepository.save(manager);

        return new MessageResponse(HttpServletResponse.SC_CREATED, "successfully");
    }

    public PaginationDTO findPitchByNecessary(Date date, long locationId, long typeId, long durationId,
                                              int pageNumber, int pageSize) {

        List<Manager> managers = managerRepository.findAllByLocationId(locationId);
        List<PitchDetailsShowDTO> list = new ArrayList<>();

        for (Manager manager : managers) {
            List<PitchDetails> pitchDetailsList = pitchDetailsRepository.
                    findAllByPitch_Manager_IdAndPitch_Type_IdAndDuration_Id(manager.getId(), typeId, durationId);

            for (PitchDetails pitchDetails : pitchDetailsList) {

                if (!bookingRepository.existsByPitchDetailsIdAndDateAndStatusId(
                        pitchDetails.getId(), date, EStatus.created)) {

                    list.add(pitchDetailsMapper.toDTOShow(pitchDetails));
                }
            }
        }

        Page<PitchDetailsShowDTO> page = new PageImpl<>(list, PageRequest.of(pageNumber, pageSize), list.size());

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public PaginationDTO filterPitch(Long typeId, Long locationId, Float rating, int pageNumber, int pageSize) {
        Page<PitchShowDTO> page;

        page = pitchRepository
                .filterPitch(typeId, locationId, rating, PageRequest.of(pageNumber, pageSize))
                .map(pitch -> pitchMapper.toDTOShow(pitch));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public PaginationDTO filterPitchForAdmin(Long typeId, Long locationId, Float rating, int pageNumber, int pageSize) {
        Page<PitchShowDTO> page;

        Manager manager = managerService.getManagerFromToken();
        if(manager != null) {
            page = pitchRepository.filterPitchByManger(typeId, locationId, rating,
                    manager.getId(),  PageRequest.of(pageNumber, pageSize)).map(pitch -> pitchMapper.toDTOShow(pitch));
        }
        else {
            page = pitchRepository
                    .filterPitch(typeId, locationId, rating, PageRequest.of(pageNumber, pageSize))
                    .map(pitch -> pitchMapper.toDTOShow(pitch));
        }

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public PitchShowDTO findById(long id) {
        Pitch pitch = pitchRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("pỉtch id: ", id))
        );

        return pitchMapper.toDTOShow(pitch);
    }

    @Override
    public MessageResponse updatePitch(PitchCreationDTO pitchCreationDTO, long id) {

        Pitch pitchDB = pitchRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("pỉtch id: ", id))
        );

        Manager manager = managerService.getManagerFromToken();
        Pitch pitch = pitchMapper.toEntity(pitchCreationDTO.getPitchDTO());

        if (manager != null) {
            if (manager.getId() != pitchDB.getManager().getId()) {
                throw new AccessDeniedException(Collections.singletonMap("You don't have permission", null));
            }
        pitch.setManager(pitchDB.getManager());
        } else{
            pitch.setManager(managerRepository.findById(pitchCreationDTO.getManagerId()). orElse(null));
        }

        pitch.setId(id);
        pitchRepository.save(pitch);

        return new MessageResponse(HttpServletResponse.SC_CREATED, "successfully");
    }

}
