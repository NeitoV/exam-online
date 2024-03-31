package com.exam.service;

import com.exam.data.dto.PaginationDTO;
import com.exam.data.dto.exam.ExamDTO;
import com.exam.data.dto.exam.ExamShowDTO;

public interface ExamService {
    String createExam(ExamDTO examDTO);

    ExamShowDTO findTheExam(String code);

    PaginationDTO findAllExam(int pageNumber, int pageSize);

    ExamDTO findExamById(long id);
}
