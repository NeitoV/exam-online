package com.exam.service;

import com.exam.data.dto.exam.ExamResultCreationDTO;
import com.exam.data.dto.exam.ExamResultDTO;

public interface ExamResultService {
    ExamResultDTO createExamResult(ExamResultCreationDTO creationDTO);
}
