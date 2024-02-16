package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.pitch.PitchCreationDTO;
import com.football.pitch.data.dto.pitch.PitchShowDTO;
import com.football.pitch.data.dto.pitch.PitchDTO;
import com.football.pitch.data.enity.Pitch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TypeMapper.class, ManagerMapper.class})
public interface PitchMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", source = "typeDTO")
    Pitch toEntity(PitchDTO pitchDTO);

    @Mapping(target = "typeDTO", source = "type")
    PitchDTO toDTO(Pitch pitch);

    @Mapping(source = "manager", target = "managerDTO")
    @Mapping(source = "type", target = "typeDTO")
    PitchShowDTO toDTOShow(Pitch pitch);
}
