package com.football.pitch.controller;

import com.football.pitch.data.dto.manager.ManagerCreationDTO;
import com.football.pitch.data.dto.manager.ManagerDTO;
import com.football.pitch.service.ManagerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PostMapping("")
    public ResponseEntity<?> createManager(@Valid @RequestBody ManagerCreationDTO managerCreationDTO) {

        return new ResponseEntity<>(managerService.createManager(managerCreationDTO), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateManager(@PathVariable long id, @RequestBody ManagerDTO managerDTO){

        return ResponseEntity.ok(managerService.updateManager(id, managerDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/{id}")
    ResponseEntity<?> findManagerById(@PathVariable long id) {

        return ResponseEntity.ok(managerService.findById(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("/userId={userId}")
    ResponseEntity<?> findManagerByUserId(@PathVariable long userId) {

        return ResponseEntity.ok(managerService.findByUserId(userId));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('Role_Admin', 'Role_Manager')")
    @GetMapping("/locationId={locationId}")
    ResponseEntity<?> findManagerByLocationId(@PathVariable long locationId) {

        return ResponseEntity.ok(managerService.findByLocation(locationId));
    }
}
