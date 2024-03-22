package com.exam.controller;

import com.exam.data.dto.StudentResultDTO;
import com.exam.service.StudentResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/studentResult")
public class StudentResultController {
    @Autowired
    private StudentResultService studentResultService;

    @GetMapping("/{studentId}")
    public ResponseEntity<List<StudentResultDTO>> getStudentResult(@PathVariable Long studentId) {
        List<StudentResultDTO> result = studentResultService.getStudentExamResults(studentId);
        return ResponseEntity.ok(result);
    }
}
