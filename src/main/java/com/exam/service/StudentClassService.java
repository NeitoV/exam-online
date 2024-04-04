package com.exam.service;

import com.exam.data.dto.StudentClassDTO;
import com.exam.data.dto.MessageResponse;


public interface StudentClassService {
    String saveNewClass(StudentClassDTO classDTO);
}
