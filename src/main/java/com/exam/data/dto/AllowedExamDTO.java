package com.exam.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllowedExamDTO {
    private String examId;
    private String examCode;
    private String examName;
    private String lecturerName;
    private String className;
    private boolean isTake = false;
}
