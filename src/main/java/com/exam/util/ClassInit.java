package com.exam.util;

import com.exam.data.enity.Role;
import com.exam.data.enity.User;
import com.exam.data.repository.RoleRepository;
import com.exam.data.repository.UserRepository;
import com.exam.enumeration.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ClassInit {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    void inIt() {
        inIt_Role();
        inIt_User();
    }

    private void inIt_Role() {
        for (ERole eRole : ERole.values()) {

            if (!roleRepository.existsByName(eRole.toString())) {
                Role role = new Role();
                role.setName(eRole.toString());

                roleRepository.save(role);
            }
        }
    }

    private void inIt_User() {
        String email = "admin@gmail.com";
        String password = "123456";

        if (!userRepository.existsByEmail(email)) {
            User user = new User();

            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(roleRepository.findById(ERole.roleAdmin).orElse(null));

            userRepository.save(user);
        }
    }
}
