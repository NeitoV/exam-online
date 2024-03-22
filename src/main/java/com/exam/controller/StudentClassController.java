package com.exam.controller;

import com.exam.data.dto.StudentClassDTO;
import com.exam.service.StudentClassService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/class")
public class StudentClassController {
    @Autowired
    private StudentClassService classService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PostMapping("")
    public ResponseEntity<?> saveNewClass(@Valid @RequestBody StudentClassDTO myClassDTO){
        classService.saveNewClass(myClassDTO);

        return ResponseEntity.ok().build();
    }
}
