package com.football.pitch.service;

import com.football.pitch.data.dto.LocationDTO;

import java.util.List;

public interface LocationService {
    List<LocationDTO> findAll();

    LocationDTO findById(long id);
}
