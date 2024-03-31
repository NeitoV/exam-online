package com.exam.service;

import com.exam.data.dto.*;
import com.exam.data.enity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    JwtResponseDTO loginUser(LoginDTO loginDTO);

    User createUser(LoginDTO loginDTO, long roleId);

    MessageResponse changePassword(ChangePasswordDTO changePasswordDTO);

    @Transactional
    MessageResponse createRegister(RegisterDTO registerDTO);

    PaginationDTO filterUser(String keyword, long roleId, int pageNumber, int pageSize);
}
