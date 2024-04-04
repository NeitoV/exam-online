package com.exam.controller;

import com.exam.data.dto.StudentClassDTO;
import com.exam.service.StudentClassService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/class")
public class StudentClassController {
    @Autowired
    private StudentClassService classService;

    @PostMapping("")
    public ResponseEntity<?> saveNewClass(@Valid @RequestBody StudentClassDTO myClassDTO){


        return ResponseEntity.ok(classService.saveNewClass(myClassDTO));
    }
}
