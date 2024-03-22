package com.exam.service;

import com.exam.data.dto.StudentResultDTO;

import java.util.List;

public interface StudentResultService {
    List<StudentResultDTO> getStudentExamResults(Long studentId);
}
