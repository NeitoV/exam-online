package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.LocationDTO;
import com.football.pitch.data.dto.PaginationDTO;
import com.football.pitch.data.dto.RevenueShowDTO;
import com.football.pitch.data.dto.TypeDTO;
import com.football.pitch.data.enity.Manager;
import com.football.pitch.data.mapper.LocationMapper;
import com.football.pitch.data.mapper.TypeMapper;
import com.football.pitch.data.repository.*;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class RevenueServiceImpl implements RevenueService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TypePitchRepository typePitchRepository;
    @Autowired
    private RevenueRepository revenueRepository;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public PaginationDTO showRevenue(String monthOfDate, Integer day, int pageNumber, int pageSize) {
        List<Object[]> listObj = revenueRepository.revenueStatistics(monthOfDate, day);
        List<RevenueShowDTO> revenueShowDTOS;
        Map<Long, RevenueShowDTO> locationMap = new HashMap<>();

        for (Object[] objects : listObj) {

            LocationDTO locationDTO = null;
            TypeDTO typeDTO = null;
            BigDecimal total = null;

            if (objects[0] != null) {
                long typeId = (long) objects[0];
                typeDTO = typePitchRepository.findById(typeId).map(type -> typeMapper.toDTO(type)).orElse(null);
            }
            if (objects[1] != null) {

                total = (BigDecimal) objects[1];
            }

            if (objects[2] != null) {

                long locationId = (long) objects[2];
                locationDTO = locationRepository.findById(locationId).map(location -> locationMapper.toDTO(location)).orElse(null);

                if (locationMap.containsKey(locationId)) {
                    RevenueShowDTO existingDTO = locationMap.get(locationId);
                    existingDTO.getRevenues().put(typeDTO, total);

                } else {
                    RevenueShowDTO revenueShowDTO = new RevenueShowDTO();
                    Map<TypeDTO, BigDecimal> revenuesMap = new HashMap<>();

                    revenueShowDTO.setLocationDTO(locationDTO);
                    revenuesMap.put(typeDTO, total);

                    revenueShowDTO.setRevenues(revenuesMap);
                    locationMap.put(locationId, revenueShowDTO);
                }
            }
        }

        revenueShowDTOS = new ArrayList<>(locationMap.values());
        Page<RevenueShowDTO> page = new PageImpl<>(revenueShowDTOS, PageRequest.of(pageNumber, pageSize), revenueShowDTOS.size());

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());

    }

    @Override
    public RevenueShowDTO showRevenueForManager(long managerId, String monthOfDate, Integer day) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("manager id: ", managerId))
        );

        List<Object[]> listObj = revenueRepository.revenueStatisticsForManager(monthOfDate, day, managerId);
        Map<TypeDTO, BigDecimal> map = new HashMap<>();

        for (Object[] objects : listObj) {
            TypeDTO typeDTO = null;
            BigDecimal total = null;

            if (objects[0] != null) {
                long typeId = (long) objects[0];
                typeDTO = typePitchRepository.findById(typeId).map(type -> typeMapper.toDTO(type)).orElse(null);
            }
            if (objects[1] != null) {

                total = (BigDecimal) objects[1];
            }

            map.put(typeDTO, total);
        }

        LocationDTO locationDTO = locationMapper.toDTO(manager.getLocation());
        return new RevenueShowDTO(locationDTO, map);
    }
}
