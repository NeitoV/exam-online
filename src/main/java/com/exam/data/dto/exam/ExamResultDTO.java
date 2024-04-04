package com.exam.data.dto.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultDTO {
    private Long examId;
    private Long studentId;
    private int point;
}
