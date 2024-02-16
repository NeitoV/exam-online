package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.StatusDTO;
import com.football.pitch.data.enity.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    StatusDTO toDTO(Status status);
}
