package com.exam.service;

import com.exam.data.dto.AllowedExamDTO;
import com.exam.data.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    List<StudentDTO> findAllStudent();

    List<AllowedExamDTO> findAllAllowedExamByStudent();
}
