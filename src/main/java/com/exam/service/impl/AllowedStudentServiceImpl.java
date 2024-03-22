package com.exam.service.impl;

import com.exam.data.dto.MessageResponse;
import com.exam.data.enity.AllowedStudent;
import com.exam.data.enity.Exam;
import com.exam.data.enity.Student;
import com.exam.data.repository.AllowedStudentRepository;
import com.exam.data.repository.ExamRepository;
import com.exam.data.repository.StudentRepository;
import com.exam.exception.ResourceNotFoundException;
import com.exam.service.AllowedStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllowedStudentServiceImpl implements AllowedStudentService {
    @Autowired
    private AllowedStudentRepository allowedStudentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExamRepository examRepository;

    public MessageResponse createAllowedStudent(Long examId, List<Long> studentIdS) {
        Exam exam = examRepository.findById(examId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("exam id: ", examId))
        );

        List<Student> students = studentIdS.stream()
                        .map(studentId -> studentRepository.findById(studentId).orElseThrow(
                                () -> new ResourceNotFoundException(
                                        Collections.singletonMap("student id: ", studentId))))
                .collect(Collectors.toList());

        List<AllowedStudent> allowedStudents = students.stream()
                .map(student -> {
                    AllowedStudent allowedStudent = new AllowedStudent();

                    allowedStudent.setStudent(student);
                    allowedStudent.setExam(exam);

                    return allowedStudent;
                }).collect(Collectors.toList());

        allowedStudentRepository.saveAll(allowedStudents);

        return new MessageResponse(HttpServletResponse.SC_CREATED, "successfully");
    }
}
