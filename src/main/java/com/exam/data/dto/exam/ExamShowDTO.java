package com.exam.data.dto.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamShowDTO {
    private List<Long> positionQuestions;
    private ExamDTO examDTO;

    private String randomCode;
}
