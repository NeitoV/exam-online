package com.football.pitch.controller;

import com.football.pitch.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("")
    public ResponseEntity<?> findAllLocation() {

        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findLocationById(@PathVariable long id) {

        return ResponseEntity.ok(locationService.findById(id));
    }
}
