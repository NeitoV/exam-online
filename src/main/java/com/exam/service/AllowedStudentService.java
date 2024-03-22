package com.exam.service;

import com.exam.data.dto.MessageResponse;

import java.util.List;

public interface AllowedStudentService {
    MessageResponse createAllowedStudent(Long examId, List<Long> studentIdS);
}
