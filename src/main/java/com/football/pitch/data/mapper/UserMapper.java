package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.LoginDTO;
import com.football.pitch.data.dto.UserShowDTO;
import com.football.pitch.data.enity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    User toEntity(LoginDTO loginDTO);

    @Mapping(source ="role", target = "roleDTO")
    UserShowDTO toDTOShow(User user);
}
