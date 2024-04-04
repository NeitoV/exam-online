package com.exam.controller;

import com.exam.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("")
    public ResponseEntity<?> findAllStudent() {

        return ResponseEntity.ok(studentService.findAllStudent());
    }

    @GetMapping("/allowed")
    public ResponseEntity<?> findAllAllowed() {

        return ResponseEntity.ok(studentService.findAllAllowedExamByStudent());
    }
}
