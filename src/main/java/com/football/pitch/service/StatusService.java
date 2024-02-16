package com.football.pitch.service;

import com.football.pitch.data.dto.StatusDTO;

import java.util.List;

public interface StatusService {
    List<StatusDTO> findAll();

    StatusDTO findById(long id);
}
