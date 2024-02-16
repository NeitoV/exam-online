package com.football.pitch.controller;

import com.football.pitch.data.dto.ChangePasswordDTO;
import com.football.pitch.data.dto.LoginDTO;
import com.football.pitch.data.dto.customer.CustomerCreationDTO;
import com.football.pitch.data.dto.manager.ManagerCreationDTO;
import com.football.pitch.service.CustomerService;
import com.football.pitch.service.ManagerService;
import com.football.pitch.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;


    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {

        return ResponseEntity.ok(userService.changePassword(changePasswordDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/filter")
    public ResponseEntity<?> filterUser(@RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(defaultValue = "") String keyword,
                                        @RequestParam(defaultValue = "0") Long roleId) {

        return ResponseEntity.ok(userService.filterUser(keyword, roleId, pageNumber, pageSize));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.findById(id));
    }
}