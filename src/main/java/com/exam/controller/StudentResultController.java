package com.exam.controller;

import com.exam.data.dto.PaginationDTO;
import com.exam.service.ExamResultService;
import com.exam.service.StudentResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/student/result")
public class StudentResultController {
    @Autowired
    private StudentResultService studentResultService;
    @Autowired
    private ExamResultService examResultService;

    @GetMapping("")
    public ResponseEntity<?> getStudentResult(@RequestParam(defaultValue = "0") int pageNumber,
                                              @RequestParam(defaultValue = "10") int pageSize) {

        PaginationDTO result = studentResultService.getStudentExamResults(pageNumber, pageSize);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExamResultDetailsById(@PathVariable long id) {

        return ResponseEntity.ok(examResultService.getResultDetailsById(id));
    }
}
