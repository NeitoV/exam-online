package com.exam.service;

import com.exam.data.dto.StudentClassDTO;
import com.exam.data.dto.MessageResponse;


public interface StudentClassService {
    MessageResponse saveNewClass(StudentClassDTO classDTO);
}
