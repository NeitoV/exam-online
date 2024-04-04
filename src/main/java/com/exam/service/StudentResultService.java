package com.exam.service;

import com.exam.data.dto.PaginationDTO;

public interface StudentResultService {
    PaginationDTO getStudentExamResults(int pageNumber, int pageSize);
}
