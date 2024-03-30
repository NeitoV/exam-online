package com.exam.data.mapper;

import com.exam.data.dto.RoleDTO;
import com.exam.data.enity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO toDTO(Role role);
}
