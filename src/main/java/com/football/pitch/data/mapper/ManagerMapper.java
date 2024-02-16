package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.manager.ManagerCreationDTO;
import com.football.pitch.data.dto.manager.ManagerDTO;
import com.football.pitch.data.enity.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface ManagerMapper {

    @Mapping(ignore = true, target = "id")
    @Mapping(source = "locationDTO", target = "location")
    Manager toEntity(ManagerDTO managerDTO);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "location", target = "locationDTO")
    ManagerDTO toDTO(Manager manager);
}
