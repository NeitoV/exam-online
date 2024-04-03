package com.exam.service.impl;

import com.exam.data.dto.MessageResponse;
import com.exam.data.dto.QuestionDTO;
import com.exam.data.enity.Exam;
import com.exam.data.enity.Lecturer;
import com.exam.data.enity.Question;
import com.exam.data.enity.User;
import com.exam.data.mapper.QuestionMapper;
import com.exam.data.repository.*;
import com.exam.exception.AccessDeniedException;
import com.exam.exception.ResourceNotFoundException;
import com.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private ExamResultRepository examResultRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LecturerRepository lecturerRepository;

    public MessageResponse updateQuestion(long questionId, QuestionDTO questionDTO) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("question questionId: ", questionId))
        );

        Lecturer lecturer = getLecturerByToken();
        if (lecturer.getId() != question.getExam().getLecturer().getId()) { // can't update anyone's exam
            throw new AccessDeniedException(Collections.singletonMap("message", "The exam does not belong to you"));
        }

        if (examResultRepository.existsByExamId(question.getExam().getId()))
            throw new AccessDeniedException(Collections.singletonMap("message", "The exam has been token by student"));


        Question update = questionMapper.toEntity(questionDTO);
        update.setId(questionId);
        update.setExam(question.getExam());

        questionRepository.save(update);

        return new MessageResponse(HttpServletResponse.SC_OK, "successfully");
    }

    public MessageResponse addNewQuestionToExam(long examId, QuestionDTO questionDTO) {

        Exam exam = examRepository.findById(examId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("exam id: ", examId))
        );

        Lecturer lecturer = getLecturerByToken();
        if (lecturer.getId() != exam.getLecturer().getId()) { // can't update anyone's exam
            throw new AccessDeniedException(Collections.singletonMap("message", "The exam does not belong to you"));
        }

        if (examResultRepository.existsByExamId(exam.getId()))
            throw new AccessDeniedException(Collections.singletonMap("message", "The exam has been token by student"));

        Question question = questionMapper.toEntity(questionDTO);
        question.setExam(exam);

        questionRepository.save(question);

        return new MessageResponse(HttpServletResponse.SC_OK, "successfully");
    }

    public MessageResponse deleteQuestion(long id) {

        Question question = questionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("question questionId: ", id))
        );

        Lecturer lecturer = getLecturerByToken();
        if (lecturer.getId() != question.getExam().getLecturer().getId()) { // can't update anyone's exam
            throw new AccessDeniedException(Collections.singletonMap("message", "The exam does not belong to you"));
        }

        if (examResultRepository.existsByExamId(question.getExam().getId()))
            throw new AccessDeniedException(Collections.singletonMap("message", "The exam has been token by student"));

        questionRepository.deleteById(id);

        return new MessageResponse(HttpServletResponse.SC_OK, "successfully");
    }

    public Lecturer getLecturerByToken() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "Not authentication")));

        Lecturer lecturer = lecturerRepository.findByUserId(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "lecture is null"))
        );

        return lecturer;
    }
}
