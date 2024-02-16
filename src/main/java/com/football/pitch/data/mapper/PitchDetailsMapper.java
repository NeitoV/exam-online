package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.pitch.PitchDetailsShowDTO;
import com.football.pitch.data.enity.PitchDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DurationMapper.class, PitchMapper.class})
public interface PitchDetailsMapper {

    @Mapping(target = "pitchDTO", source = "pitch")
    @Mapping(target = "durationDTO", source = "duration")
    @Mapping(target = "pricePerHour", source = "price.pricePerHour")
    PitchDetailsShowDTO toDTOShow(PitchDetails pitchDetails);
}
