package com.exam.service.impl;

import com.exam.data.dto.ChangePasswordDTO;
import com.exam.data.dto.JwtResponseDTO;
import com.exam.data.dto.LoginDTO;
import com.exam.data.dto.MessageResponse;
import com.exam.data.enity.Role;
import com.exam.data.enity.User;
import com.exam.data.repository.RoleRepository;
import com.exam.data.repository.UserRepository;
import com.exam.exception.AccessDeniedException;
import com.exam.exception.ConflictException;
import com.exam.exception.ExceptionCustom;
import com.exam.exception.ResourceNotFoundException;
import com.exam.service.JwtService;
import com.exam.service.UserService;
import com.exam.data.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
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
    @Autowired
    private UserMapper userMapper;

    @Override
    public JwtResponseDTO loginUser(LoginDTO loginDTO) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());

        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("email: ", loginDTO.getEmail()))
        );

        if (user.isDeleted()) {
            throw new AccessDeniedException(Collections.singletonMap("user is deleted", null));
        }

        if (!checkValidPassword(loginDTO.getPassword(), user.getPassword()))
            throw new AccessDeniedException(Collections.singletonMap("password is wrong", null));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                        loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Role role = user.getRole();

        String jwt = jwtService.generateToken(userDetails);

        return new JwtResponseDTO(jwt, loginDTO.getEmail(), role.getName());
    }

    @Override
    public User createUser(LoginDTO loginDTO, long roleId) {
        if (userRepository.existsByEmail(loginDTO.getEmail()))
            throw new ConflictException(Collections.singletonMap("email: ", loginDTO.getEmail()));

        User user = userMapper.toEntity(loginDTO);

        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
        user.setRole(roleRepository.findById(roleId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("Role id", roleId))
        ));

        return userRepository.save(user);
    }

    @Override
    public MessageResponse changePassword(ChangePasswordDTO changePasswordDTO) {
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword()))
            throw new ExceptionCustom("Password confirm not same", null);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ExceptionCustom("Not authentication", null));

        List<? extends GrantedAuthority> authorities = authentication.getAuthorities()
                .stream().collect(Collectors.toList());


        if (!checkValidPassword(changePasswordDTO.getNewPassword(), user.getPassword())) {
            if (!passwordValid(changePasswordDTO.getNewPassword()))
                throw new ExceptionCustom("Password not strong", null);
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new ExceptionCustom("New password must be different from old", null);
        }

        return new MessageResponse(HttpServletResponse.SC_OK, "successfully");
    }

    private Boolean checkValidPassword(String password, String passwordEncoded) {

        return passwordEncoder.matches(password, passwordEncoded);
    }

    private boolean passwordValid(String password) {
        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }





}