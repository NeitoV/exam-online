package com.football.pitch.controller;

import com.football.pitch.data.dto.LoginDTO;
import com.football.pitch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO loginDTO) {

        return ResponseEntity.ok(userService.loginUser(loginDTO));
    }
}
