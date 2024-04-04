package com.exam.controller;


import com.exam.data.dto.QuestionDTO;
import com.exam.service.QuestionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Lecturer')")
    @PutMapping("/{id}")
    ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO) {

        return ResponseEntity.ok(questionService.updateQuestion(id, questionDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Lecturer')")
    @PostMapping("/examId={id}")
    ResponseEntity<?> createNewQuestionOnExam(@PathVariable Long id, @RequestBody QuestionDTO questionDTO) {

        return new ResponseEntity<>(questionService.addNewQuestionToExam(id, questionDTO), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Lecturer')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteQuestion(@PathVariable Long id) {

        return ResponseEntity.ok(questionService.deleteQuestion(id));
    }
}
