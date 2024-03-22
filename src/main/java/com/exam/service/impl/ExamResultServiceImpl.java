package com.exam.service.impl;

import com.exam.data.dto.exam.ExamResultCreationDTO;
import com.exam.data.dto.exam.ExamResultDTO;
import com.exam.data.enity.*;
import com.exam.data.repository.*;
import com.exam.exception.AccessDeniedException;
import com.exam.exception.ExceptionCustom;
import com.exam.exception.ResourceNotFoundException;
import com.exam.service.ExamResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ExamResultServiceImpl implements ExamResultService {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExamResultRepository examResultRepository;
    @Autowired
    private ExamResultDetailsRepository examResultDetailsRepository;


    public ExamResultDTO createExamResult(ExamResultCreationDTO creationDTO) {
        if (creationDTO.getPositionQuestions().size() != creationDTO.getSelectedAnswer().size()) {
            throw new ExceptionCustom("the answer must be fully filled");
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("Not authentication", null)));

        Student student = studentRepository.findByUserId(user.getId()).orElseThrow(
                () -> new AccessDeniedException(Collections.singletonMap("You aren't a student", null))
        );

        Exam exam = examRepository.findById(creationDTO.getExamId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("exam id: ", creationDTO.getExamId()))
        );

        if (examResultRepository.existsByExamIdAndStudentId(exam.getId(), student.getId())) {
            throw new AccessDeniedException(Collections.singletonMap("You have been done yet", null));
        }

        ExamResult examResult = new ExamResult();
        examResult.setExam(exam);
        examResult.setStudent(student);

        examResult = examResultRepository.save(examResult);

        List<Long> questionId = creationDTO.getPositionQuestions();
        List<String> answers = creationDTO.getSelectedAnswer();
        int totalCorrect = 0;

        for (int i = 0; i < questionId.size(); i++) {

            Question question = questionRepository.findById(questionId.get(i)).orElse(null);

            if (answers.get(i).equals(question.getCorrectAnswer())) {
                totalCorrect++;
            }

            ExamResultDetails examResultDetails = new ExamResultDetails();
            examResultDetails.setExamResult(examResult);
            examResultDetails.setQuestion(question);
            examResultDetails.setSelectedAnswer(answers.get(i));

            examResultDetailsRepository.save(examResultDetails);
        }

        examResult.setPoint(totalCorrect);
        examResultRepository.save(examResult);

        return new ExamResultDTO(exam.getId(), student.getId(), totalCorrect);
    }
}
