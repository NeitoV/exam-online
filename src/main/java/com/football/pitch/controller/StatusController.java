package com.football.pitch.controller;

import com.football.pitch.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/status")
public class StatusController {
    @Autowired
    private StatusService statusService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok(statusService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findStatusById(@PathVariable long id) {

        return ResponseEntity.ok(statusService.findById(id));
    }
}
