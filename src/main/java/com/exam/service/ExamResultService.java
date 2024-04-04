package com.exam.service;

import com.exam.data.dto.exam.ExamResultCreationDTO;
import com.exam.data.dto.exam.ExamResultDTO;
import com.exam.data.dto.result.ResulDetailsShowDTO;

public interface ExamResultService {
    ExamResultDTO createExamResult(ExamResultCreationDTO creationDTO);

    ResulDetailsShowDTO getResultDetailsById(long id);
}
