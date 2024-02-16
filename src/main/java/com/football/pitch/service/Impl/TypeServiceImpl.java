package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.TypeDTO;
import com.football.pitch.data.enity.Type;
import com.football.pitch.data.mapper.TypeMapper;
import com.football.pitch.data.repository.TypePitchRepository;
import com.football.pitch.enumeration.ETypePitch;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypePitchRepository typePitchRepository;
    @Autowired
    private TypeMapper typeMapper;

    public List<TypeDTO> findAll() {

        return typePitchRepository.findAll().stream().map(type -> typeMapper.toDTO(type)).collect(Collectors.toList());
    }

    @Override
    public TypeDTO findById(long id) {

        return typePitchRepository.findById(id).map(type -> typeMapper.toDTO(type)).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("type id: ", id))
        );
    }
}
