package com.exam.data.dto.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamBasicInformationDTO {
    private Long id;
    private LocalDate expiryDate;
    private int durationMinutes;
    private String name;
}
