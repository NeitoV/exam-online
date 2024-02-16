package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.PeriodDTO;
import com.football.pitch.data.enity.Period;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PeriodMapper {
    PeriodDTO toDTO(Period period);
}
