package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.*;
import com.football.pitch.data.enity.Customer;
import com.football.pitch.data.enity.Manager;
import com.football.pitch.data.enity.Role;
import com.football.pitch.data.enity.User;
import com.football.pitch.data.mapper.UserMapper;
import com.football.pitch.data.repository.*;
import com.football.pitch.enumeration.EStatus;
import com.football.pitch.exception.AccessDeniedException;
import com.football.pitch.exception.ConflictException;
import com.football.pitch.exception.ExceptionCustom;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.service.JwtService;
import com.football.pitch.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
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
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ManagerRepository managerRepository;

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

    @Override
    public MessageResponse deleteUser(long id) {
        if (bookingRepository.existsByUserIdAndStatusId(id, EStatus.created)) {
            throw new AccessDeniedException(Collections.singletonMap("user has booked schedule", null));
        }

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("user id: ", id)));

        user.setDeleted(true);
        userRepository.save(user);

        return new MessageResponse(HttpServletResponse.SC_OK, "successfully");
    }

    @Override
    public PaginationDTO filterUser(String keyword, long roleId, int pageNumber, int pageSize) {

        Page<User> page = userRepository.filterUser(keyword, roleId, PageRequest.of(pageNumber, pageSize));
        List<UserShowDTO> list = new ArrayList<>();

        for (User user : page.getContent()) {

            UserShowDTO userShowDTO = mapToDTO(user);
            list.add(userShowDTO);
        }

        return new PaginationDTO(list, page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public UserShowDTO findById(long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("user id: ", id)));

        return mapToDTO(user);
    }

    private UserShowDTO mapToDTO(User user) {
        if (user.isDeleted())
            throw new AccessDeniedException(Collections.singletonMap("user is deleted", null));

        UserShowDTO userShowDTO = userMapper.toDTOShow(user);

        int roleIdDb = user.getRole().getId().intValue();
        switch (roleIdDb) {
            case 2: {
                Customer customer = customerRepository.findByUserId(user.getId()).orElseThrow(
                        () -> new ResourceNotFoundException(Collections.singletonMap("customer: ", null))
                );

                userShowDTO.setFullName(customer.getFullName());
                userShowDTO.setPhoneNumber(customer.getPhoneNumber());
                userShowDTO.setBirthday(customer.getBirthday());
                break;
            }

            case 3: {
                Manager manager = managerRepository.findByUserId(user.getId()).orElseThrow(
                        () -> new ResourceNotFoundException(Collections.singletonMap("manager: ", null))
                );

                userShowDTO.setFullName(manager.getFullName());
                userShowDTO.setPhoneNumber(manager.getPhoneNumber());
                userShowDTO.setBirthday(manager.getBirthday());
                break;
            }

            default:
                throw new ResourceNotFoundException(Collections.singletonMap("role id: ", roleIdDb));
        }

        return userShowDTO;
    }
}