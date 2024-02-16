package com.football.pitch.service;

import com.football.pitch.data.dto.*;
import com.football.pitch.data.enity.User;

public interface UserService {
    JwtResponseDTO loginUser(LoginDTO loginDTO);

    User createUser(LoginDTO loginDTO, long roleId);

    MessageResponse changePassword(ChangePasswordDTO changePasswordDTO);

    MessageResponse deleteUser(long id);

    PaginationDTO filterUser(String keyword, long roleId, int pageNumber, int pageSize);

    UserShowDTO findById(long id);
}
