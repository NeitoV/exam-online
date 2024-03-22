package com.exam.data.dto.exam;

import com.exam.data.dto.QuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO {
    private Long id;
    private LocalDate expiryDate;
    private int durationMinutes;
    private String name;
    private List<QuestionDTO> questionDTOS;
}
