package com.exam.service;

import com.exam.data.dto.MessageResponse;
import com.exam.data.dto.PaginationDTO;
import com.exam.data.dto.exam.ExamDTO;
import com.exam.data.dto.exam.ExamShowDTO;
import org.springframework.transaction.annotation.Transactional;

public interface ExamService {
    @Transactional
    String createExam(ExamDTO examDTO);

    ExamShowDTO findTheExam(String code);

    PaginationDTO findAllExam(int pageNumber, int pageSize);

    ExamDTO findExamById(long id);


    @Transactional
    MessageResponse deleteExamById(long id);
}
