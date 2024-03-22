package com.exam.controller;

import com.exam.service.AllowedStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/allowed")
public class AllowedStudentController {
    @Autowired
    private AllowedStudentService allowedStudentService;

    @PostMapping("/{examId}")
    ResponseEntity<?> createAllowedStudentForExam(@PathVariable Long examId, @RequestBody List<Long> studentIdS) {

        return new ResponseEntity<>(allowedStudentService.createAllowedStudent(examId, studentIdS), HttpStatus.CREATED);
    }
}
