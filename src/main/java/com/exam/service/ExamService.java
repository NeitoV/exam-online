package com.exam.service;

import com.exam.data.dto.exam.ExamDTO;
import com.exam.data.dto.exam.ExamShowDTO;

public interface ExamService {
    String createExam(ExamDTO examDTO);

    ExamShowDTO findTheExam(String code);
}
