package com.football.pitch.service;

import com.football.pitch.data.dto.MessageResponse;
import com.football.pitch.data.dto.PaginationDTO;
import com.football.pitch.data.dto.pitch.PitchShowDTO;
import com.football.pitch.data.dto.pitch.PitchCreationDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface PitchService {


    @Transactional
    MessageResponse createPitch(PitchCreationDTO pitchCreationDTO);


    PaginationDTO findPitchByNecessary(Date date, long locationId, long typeId,
                                       long durationId, int pageNumber, int pageSize);


    PaginationDTO filterPitch(Long typeId, Long locationId, Float rating, int pageNumber, int pageSize);

    PaginationDTO filterPitchForAdmin(Long typeId, Long locationId, Float rating, int pageNumber, int pageSize);

    PitchShowDTO findById(long id);

    MessageResponse updatePitch(PitchCreationDTO pitchCreationDTO, long id);
}
