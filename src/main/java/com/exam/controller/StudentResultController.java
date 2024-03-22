package com.exam.controller;

import com.exam.data.dto.PaginationDTO;
import com.exam.data.dto.StudentResultDTO;
import com.exam.service.StudentResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studentResult")
public class StudentResultController {
    @Autowired
    private StudentResultService studentResultService;

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentResult(@PathVariable Long studentId,
                                              @RequestParam(defaultValue = "0") int pageNumber,
                                              @RequestParam(defaultValue = "10") int pageSize) {
        PaginationDTO result = studentResultService.getStudentExamResults(studentId, pageNumber, pageSize);
        return ResponseEntity.ok(result);
    }
}
