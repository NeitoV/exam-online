package com.exam.controller;

import com.exam.data.dto.exam.ExamDTO;
import com.exam.data.dto.exam.ExamResultCreationDTO;
import com.exam.service.ExamResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/exam-result")
public class ExamResultController {
    @Autowired
    private ExamResultService examResultService;

    @PostMapping("")
    public ResponseEntity<?> createExam(@RequestBody ExamResultCreationDTO creationDTO) {

        return new ResponseEntity<>(examResultService.createExamResult(creationDTO), HttpStatus.CREATED);
    }
}
