package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.RoleDTO;
import com.football.pitch.data.enity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toDTO(Role role);
}
