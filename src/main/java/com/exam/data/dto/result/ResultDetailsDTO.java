package com.exam.data.dto.result;

import com.exam.data.dto.QuestionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDetailsDTO {
    private QuestionDTO questionDTO;
    private String selectedAnswer;
}
