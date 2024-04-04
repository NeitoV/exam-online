package com.exam.service.impl;

import com.exam.data.dto.AllowedExamDTO;
import com.exam.data.dto.StudentDTO;
import com.exam.data.enity.AllowedStudent;
import com.exam.data.enity.ExamResult;
import com.exam.data.enity.Student;
import com.exam.data.enity.User;
import com.exam.data.mapper.StudentMapper;
import com.exam.data.repository.AllowedStudentRepository;
import com.exam.data.repository.ExamResultRepository;
import com.exam.data.repository.StudentRepository;
import com.exam.data.repository.UserRepository;
import com.exam.exception.AccessDeniedException;
import com.exam.exception.ResourceNotFoundException;
import com.exam.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private AllowedStudentRepository allowedStudentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExamResultRepository examResultRepository;

    public List<StudentDTO> findAllStudent() {

        return studentRepository.findAll().stream()
                .map(student -> studentMapper.toDTO(student)).collect(Collectors.toList());
    }

    @Override
    public List<AllowedExamDTO> findAllAllowedExamByStudent() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("message", "Not authentication")));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AccessDeniedException(Collections.singletonMap("message", "You aren't a student")));

        List<AllowedExamDTO> allowedStudents = allowedStudentRepository.findAllByStudentId(student.getId()).stream()
                .map(allowedStudent -> {
                    AllowedExamDTO allowedExamDTO = studentMapper.toAllowedExamDTO(allowedStudent);

                    allowedExamDTO.setTake(
                            examResultRepository.existsByExamIdAndStudentId(allowedStudent.getExam().getId(), student.getId()));

                    return allowedExamDTO;
                }).collect(Collectors.toList());

        return allowedStudents;
    }
}
