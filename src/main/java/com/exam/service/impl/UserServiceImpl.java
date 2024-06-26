package com.exam.service.impl;

import com.exam.data.dto.*;
import com.exam.data.enity.Lecturer;
import com.exam.data.enity.Role;
import com.exam.data.enity.Student;
import com.exam.data.enity.User;
import com.exam.data.repository.LecturerRepository;
import com.exam.data.repository.RoleRepository;
import com.exam.data.repository.StudentRepository;
import com.exam.data.repository.UserRepository;
import com.exam.enumeration.ERole;
import com.exam.exception.AccessDeniedException;
import com.exam.exception.ConflictException;
import com.exam.exception.ExceptionCustom;
import com.exam.exception.ResourceNotFoundException;
import com.exam.service.JwtService;
import com.exam.service.UserService;
import com.exam.data.mapper.UserMapper;

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
    private StudentRepository studentRepository;
    @Autowired
    private LecturerRepository lecturerRepository;

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
            throw new AccessDeniedException(Collections.singletonMap("message", "user is deleted"));
        }

        if (!checkValidPassword(loginDTO.getPassword(), user.getPassword()))
            throw new AccessDeniedException(Collections.singletonMap("message", "password is wrong"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                        loginDTO.getPassword()));

//        SecurityContextHolder.getContext().setAuthentication(authentication);

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
            throw new ExceptionCustom("message", "Password confirm not same");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ExceptionCustom("message", "Not authentication"));

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

    @Override
    public MessageResponse createRegister(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent())
            throw new ConflictException(Collections.singletonMap("email", registerDTO.getEmail()));

        if (!registerDTO.getPassword().equals(registerDTO.getPasswordConfirm())) {
            throw new ExceptionCustom("Passwords do not match!");
        }

        if (registerDTO.getRoleId() == ERole.roleAdmin) {
            throw new ResourceNotFoundException(Collections.singletonMap("message", "admin access not allow"));
        } else {

            Role role = roleRepository.findById(registerDTO.getRoleId()).orElseThrow(
                    () -> new ResourceNotFoundException(Collections.singletonMap("message", "role do not exist"))
            );

            User user = new User();

            user.setEmail(registerDTO.getEmail());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setRole(role);

            userRepository.save(user);

            User savedUser = userRepository.save(user);

            if (registerDTO.getRoleId() == ERole.roleStudent) {
                Student student = new Student();

                student.setName(registerDTO.getName());
                student.setUser(savedUser);

                studentRepository.save(student);
            }
            if (registerDTO.getRoleId() == ERole.roleLecturer) {
                Lecturer lecturer = new Lecturer();

                lecturer.setName(registerDTO.getName());
                lecturer.setUser(savedUser);

                lecturerRepository.save(lecturer);
            }
        }
        return new MessageResponse(HttpServletResponse.SC_OK, "Tao user thanh cong");
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

    private UserShowDTO mapToDTO(User user) {
        if (user.isDeleted())
            throw new AccessDeniedException(Collections.singletonMap("message", "user is deleted"));

        UserShowDTO userShowDTO = userMapper.toDTOShow(user);

        int roleIdDb = user.getRole().getId().intValue();
        switch (roleIdDb) {
            case 3: {
                Student student = studentRepository.findByUserId(user.getId()).orElseThrow(
                        () -> new ResourceNotFoundException(Collections.singletonMap("message", "student is null"))

                );
                userShowDTO.setName(student.getName());

                break;
            }

            case 2: {
                Lecturer lecturer = lecturerRepository.findByUserId(user.getId()).orElseThrow(
                        () -> new ResourceNotFoundException(Collections.singletonMap("message", "lecturer is null"))
                );
                userShowDTO.setName(lecturer.getName());
                break;
            }

            default:
                throw new ResourceNotFoundException(Collections.singletonMap("message", "this role does not found"));
        }

        return userShowDTO;
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

}