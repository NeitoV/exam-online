package com.exam.service;

import com.exam.data.dto.MessageResponse;
import com.exam.data.dto.QuestionDTO;

public interface QuestionService {
    MessageResponse updateQuestion(long questionId, QuestionDTO questionDTO);

    MessageResponse addNewQuestionToExam(long examId, QuestionDTO questionDTO);

    MessageResponse deleteQuestion(long id);
}
