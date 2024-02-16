package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.LocationDTO;
import com.football.pitch.data.enity.Location;
import com.football.pitch.data.mapper.LocationMapper;
import com.football.pitch.data.repository.LocationRepository;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<LocationDTO> findAll() {

        return locationRepository.findAll().stream()
                .map(location -> locationMapper.toDTO(location)).collect(Collectors.toList());
    }

    @Override
    public LocationDTO findById(long id) {

        return locationRepository.findById(id).map(location -> locationMapper.toDTO(location)).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("location id: ", id))
        );
    }

}
