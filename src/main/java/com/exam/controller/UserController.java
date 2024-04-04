package com.exam.controller;

import com.exam.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/filter")
    public ResponseEntity<?> filterUser(@RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(defaultValue = "") String keyword,
                                        @RequestParam(defaultValue = "0") long roleId) {

        return ResponseEntity.ok(userService.filterUser(keyword, roleId, pageNumber, pageSize));
    }
}
