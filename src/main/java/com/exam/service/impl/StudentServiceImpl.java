package com.exam.service.impl;

import com.exam.data.dto.StudentDTO;
import com.exam.data.mapper.StudentMapper;
import com.exam.data.repository.StudentRepository;
import com.exam.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentMapper studentMapper;

    public List<StudentDTO> findAllStudent() {

        return studentRepository.findAll().stream()
                .map(student -> studentMapper.toDTO(student)).collect(Collectors.toList());
    }
}
