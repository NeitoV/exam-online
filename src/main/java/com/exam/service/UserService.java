package com.exam.service;

import com.exam.data.dto.ChangePasswordDTO;
import com.exam.data.dto.JwtResponseDTO;
import com.exam.data.dto.LoginDTO;
import com.exam.data.dto.MessageResponse;
import com.exam.data.enity.User;

public interface UserService {
    JwtResponseDTO loginUser(LoginDTO loginDTO);

    User createUser(LoginDTO loginDTO, long roleId);

    MessageResponse changePassword(ChangePasswordDTO changePasswordDTO);

}
