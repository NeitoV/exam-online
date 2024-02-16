package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.LocationDTO;
import com.football.pitch.data.enity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "id", ignore = true)
    Location toEntity(LocationDTO locationDTO);

    LocationDTO toDTO(Location location);
}
