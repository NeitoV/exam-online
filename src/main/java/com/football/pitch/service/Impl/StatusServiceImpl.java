package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.StatusDTO;
import com.football.pitch.data.mapper.StatusMapper;
import com.football.pitch.data.repository.StatusRepository;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private StatusMapper statusMapper;

    @Override
    public List<StatusDTO> findAll() {
        return statusRepository.findAll().stream().map(status -> statusMapper.toDTO(status)).collect(Collectors.toList());
    }

    @Override
    public StatusDTO findById(long id) {

        return statusRepository.findById(id).map(status -> statusMapper.toDTO(status)).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("status id: ", id))
        );
    }
}
