package com.football.pitch.controller;

import com.football.pitch.data.dto.pitch.PitchCreationDTO;
import com.football.pitch.data.dto.pitch.PitchDTO;
import com.football.pitch.service.PitchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/pitch")
public class PitchController {
    @Autowired
    private PitchService pitchService;


    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('Role_Admin', 'Role_Manager')")
    @PostMapping(value = "")
    public ResponseEntity<?> createPitches(@RequestBody PitchCreationDTO pitchCreationDTO) {

        return new ResponseEntity<>(pitchService.createPitch(pitchCreationDTO), HttpStatus.CREATED);
    }


    @GetMapping("/find")
    public ResponseEntity<?> findPitch(@RequestParam Long locationId, @RequestParam Long typeId,
                                       @RequestParam Long durationId,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                       @RequestParam(defaultValue = "0") int pageNumber,
                                       @RequestParam(defaultValue = "10") int pageSize) {

        return ResponseEntity.ok(
                pitchService.findPitchByNecessary(date, locationId, typeId, durationId, pageNumber, pageSize));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('Role_Admin', 'Role_Manager')")
    @GetMapping("/filter")
    public ResponseEntity<?> filterPitch(@RequestParam(required = false) Long typeId,
                                         @RequestParam(required = false) Long locationId,
                                         @RequestParam(defaultValue = "0") Float rating,
                                         @RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int pageSize) {

        return ResponseEntity.ok(pitchService.filterPitchForAdmin(typeId, locationId, rating, pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPitchById(@PathVariable Long id) {

        return ResponseEntity.ok(pitchService.findById(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('Role_Admin', 'Role_Manager')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePitch(@PathVariable Long id, @RequestBody PitchCreationDTO pitchCreationDTO) {

        return ResponseEntity.ok(pitchService.updatePitch(pitchCreationDTO, id));
    }

    @GetMapping("/filter-customer")
    public ResponseEntity<?> filterPitchForCustomer(@RequestParam(required = false) Long typeId,
                                         @RequestParam(required = false) Long locationId,
                                         @RequestParam(defaultValue = "0") Float rating,
                                         @RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int pageSize) {

        return ResponseEntity.ok(pitchService.filterPitch(typeId, locationId, rating, pageNumber, pageSize));
    }
}
