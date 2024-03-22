package com.exam.data.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class StudentResultDTO {
    private String examCode;
    private String lecturerName;
    private int point;
    private boolean pass;
}
