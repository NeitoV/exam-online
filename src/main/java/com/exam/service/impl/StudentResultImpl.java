package com.exam.service.impl;

import com.exam.data.dto.PaginationDTO;
import com.exam.data.dto.StudentResultDTO;
import com.exam.data.repository.StudentResultRepository;
import com.exam.enumeration.EConstantNumber;
import com.exam.service.StudentResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentResultImpl implements StudentResultService {
    @Autowired
    private StudentResultRepository studentResultRepository;

    public PaginationDTO getStudentExamResults(Long studentId, int pageNumber, int pageSize) {
        List<Object[]> results = studentResultRepository.getStudentResult(studentId);
        List<StudentResultDTO> dtos = new ArrayList<>();

        for (Object[] result : results) {
            String examCode = (String) result[0];
            String lecturerName = (String) result[1];
            String className = (String)result[2];
            int point = (int) result[3];

            StudentResultDTO dto = new StudentResultDTO();
            dto.setExamCode(examCode);
            dto.setLecturerName(lecturerName);
            dto.setPoint(point);
            dto.setClassName(className);

            if(point >= EConstantNumber.passPoint) {
                dto.setPass(true);
            }
            else
                dto.setPass(false);

            dtos.add(dto);
        }

        Page<StudentResultDTO> page = new PageImpl<>(dtos, PageRequest.of(pageNumber, pageSize), 2);

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
//      return dtos;
    }
}
