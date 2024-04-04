package com.exam.controller;

import com.exam.data.dto.PaginationDTO;
import com.exam.data.dto.exam.ExamDTO;
import com.exam.service.ExamService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/exam")
public class ExamController {
    @Autowired
    private ExamService examService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Lecturer')")
    @PostMapping("")
    public ResponseEntity<?> createExam(@RequestBody ExamDTO examDTO) {

        return new ResponseEntity<>(examService.createExam(examDTO), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Student')")
    @GetMapping("/{code}")
    public ResponseEntity<?> findTheExamByCode(@PathVariable String code) {

        return ResponseEntity.ok(examService.findTheExam(code));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Lecturer')")
    @GetMapping("")
    public ResponseEntity<?> findAllExam(@RequestParam(defaultValue = "0") int pageNumber,
                                         @RequestParam(defaultValue = "10") int pageSize) {

        PaginationDTO result = examService.findAllExam(pageNumber, pageSize);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/exam_id={id}")
    public ResponseEntity<?> findExamById(@PathVariable long id) {

        return ResponseEntity.ok(examService.findExamById(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Lecturer')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {

        return ResponseEntity.ok(examService.deleteExamById(id));
    }
}
