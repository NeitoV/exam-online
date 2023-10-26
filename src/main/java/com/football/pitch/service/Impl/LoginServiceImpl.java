package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.JwtResponseDTO;
import com.football.pitch.data.dto.LoginDTO;
import com.football.pitch.data.enity.Role;
import com.football.pitch.data.enity.User;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.repository.UserRepository;
import com.football.pitch.repository.RoleRepository;
import com.football.pitch.service.JwtService;
import com.football.pitch.service.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public JwtResponseDTO loginUser(LoginDTO loginDTO) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                        loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("email: ", loginDTO.getEmail()))
        );

        Role role = user.getRole();

        String jwt = jwtService.generateToken(userDetails);

        return new JwtResponseDTO(jwt, loginDTO.getEmail(), role.getName());
    }

}
