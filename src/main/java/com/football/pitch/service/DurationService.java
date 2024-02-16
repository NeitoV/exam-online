package com.football.pitch.service;

import com.football.pitch.data.dto.DurationDTO;
import com.football.pitch.data.enity.Duration;

import java.util.List;

public interface DurationService {
    List<DurationDTO> findAll();

    DurationDTO findById(long id);
}
