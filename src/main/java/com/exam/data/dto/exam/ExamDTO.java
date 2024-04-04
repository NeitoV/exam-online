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
    private ExamBasicInformationDTO examBasicInformationDTO;
    private List<QuestionDTO> questionDTOS;
}
