package com.exam.service.impl;

import com.exam.data.dto.PaginationDTO;
import com.exam.data.dto.result.StudentResultDTO;
import com.exam.data.enity.Student;
import com.exam.data.enity.User;
import com.exam.data.mapper.ExamMapper;
import com.exam.data.mapper.ExamResultMapper;
import com.exam.data.repository.StudentRepository;
import com.exam.data.repository.StudentResultRepository;
import com.exam.data.repository.UserRepository;
import com.exam.exception.AccessDeniedException;
import com.exam.exception.ResourceNotFoundException;
import com.exam.service.StudentResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class StudentResultImpl implements StudentResultService {
    @Autowired
    private StudentResultRepository studentResultRepository;
    @Autowired
    private ExamResultMapper examResultMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;

    public PaginationDTO getStudentExamResults(int pageNumber, int pageSize) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("message", "Not authentication")));

        Student student = studentRepository.findByUserId(user.getId()).orElseThrow(
                () -> new AccessDeniedException(Collections.singletonMap("message", "You aren't a student"))
        );

        Page<StudentResultDTO> page = studentResultRepository
                .getStudentResult(student.getId(), PageRequest.of(pageNumber, pageSize)).map(
                        examResult -> examResultMapper.examResultToStudentResultDto(examResult));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }
}
