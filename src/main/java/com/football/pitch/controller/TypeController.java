package com.football.pitch.controller;

import com.football.pitch.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("")
    public ResponseEntity<?> findAllType() {

        return ResponseEntity.ok(typeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTypeById(@PathVariable long id) {

        return ResponseEntity.ok(typeService.findById(id));
    }
}
