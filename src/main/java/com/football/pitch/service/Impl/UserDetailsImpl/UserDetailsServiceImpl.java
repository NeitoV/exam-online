package com.football.pitch.service.Impl.UserDetailsImpl;

import com.football.pitch.data.enity.User;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.data.repository.RoleRepository;
import com.football.pitch.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(userName).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("email: ", userName))
        );

        return UserDetailsImpl.build(user);
    }

}