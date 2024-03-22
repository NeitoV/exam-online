package com.exam.service.impl;

import com.exam.data.dto.StudentResultDTO;
import com.exam.data.repository.StudentResultRepository;
import com.exam.enumeration.EConstantNumber;
import com.exam.service.StudentResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentResultImpl implements StudentResultService {
    @Autowired
    private StudentResultRepository studentResultRepository;
    @Override
    public List<StudentResultDTO> getStudentExamResults(Long studentId) {
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
        return dtos;
    }
}
