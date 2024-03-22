package com.exam.service;

import com.exam.data.dto.PaginationDTO;
import com.exam.data.dto.StudentResultDTO;

import java.util.List;

public interface StudentResultService {
    PaginationDTO getStudentExamResults(Long studentId, int pageNumber, int pageSize);
}
