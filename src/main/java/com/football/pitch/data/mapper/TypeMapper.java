package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.TypeDTO;
import com.football.pitch.data.enity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface TypeMapper {
    @Mapping(target = "name", ignore = true)
    Type toEntity(TypeDTO typeDTO);

    TypeDTO toDTO(Type  type);
}
