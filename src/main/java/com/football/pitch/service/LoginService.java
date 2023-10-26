package com.football.pitch.service;

import com.football.pitch.data.dto.JwtResponseDTO;
import com.football.pitch.data.dto.LoginDTO;

public interface LoginService {
    public JwtResponseDTO loginUser(LoginDTO loginDTO);
}
