package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.DurationDTO;
import com.football.pitch.data.enity.Duration;
import com.football.pitch.data.mapper.DurationMapper;
import com.football.pitch.data.repository.DurationRepository;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.service.DurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DurationServiceImpl implements DurationService {
    @Autowired
    private DurationRepository durationRepository;
    @Autowired
    private DurationMapper durationMapper;

    @Override
    public List<DurationDTO> findAll() {

        return durationRepository.findAll().stream()
                .map(duration -> durationMapper.toDTO(duration)).collect(Collectors.toList());
    }

    @Override
    public DurationDTO findById(long id) {

        return durationRepository.findById(id).map(duration -> durationMapper.toDTO(duration)).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("duration id: ", id))
        );
    }
}
