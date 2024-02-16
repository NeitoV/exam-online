package com.football.pitch.controller;

import com.football.pitch.service.DurationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/duration")
public class DurationController {
    @Autowired
    private DurationService durationService;

    @GetMapping("")
    public ResponseEntity<?> findAllDuration() {

        return ResponseEntity.ok(durationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findDurationById(@PathVariable Long id) {

        return ResponseEntity.ok(durationService.findById(id));
    }
}
