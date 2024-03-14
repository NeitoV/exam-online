package com.exam.data.mapper;

import com.exam.data.dto.LoginDTO;
import com.exam.data.enity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toEntity(LoginDTO loginDTO);
}
