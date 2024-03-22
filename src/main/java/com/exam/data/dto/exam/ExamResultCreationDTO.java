package com.exam.data.dto.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultCreationDTO {
    private List<Long> positionQuestions;
    private List<String> selectedAnswer;
    private Long examId;
}
