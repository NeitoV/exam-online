package com.exam.data.dto.result;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class StudentResultDTO {
    private long examResultId;
    private String examCode;
    private String examName;
    private String lecturerName;
    private String className;
    private int point;
}
