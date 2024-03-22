package com.exam.service;

import com.exam.data.dto.*;
import com.exam.data.enity.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    JwtResponseDTO loginUser(LoginDTO loginDTO);

    User createUser(LoginDTO loginDTO, long roleId);

    MessageResponse changePassword(ChangePasswordDTO changePasswordDTO);

    @Transactional
    MessageResponse saveRegister(RegisterDTO registerDTO);
}
