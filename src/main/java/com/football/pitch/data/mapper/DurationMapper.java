package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.DurationDTO;
import com.football.pitch.data.enity.Duration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PeriodMapper.class})
public interface DurationMapper {
    @Mapping(source = "period", target = "periodDTO")
    DurationDTO toDTO(Duration duration);
}
