package com.football.pitch.service;

import com.football.pitch.data.dto.TypeDTO;
import com.football.pitch.data.enity.Type;

import java.util.List;

public interface TypeService {
    List<TypeDTO> findAll();

    TypeDTO findById(long id);
}
