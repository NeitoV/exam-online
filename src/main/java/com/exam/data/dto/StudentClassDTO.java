package com.exam.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentClassDTO {
    @NotNull(message = "name khong duoc de trong")
    private String name;

//    @NotNull(message = "code random khong dc nhap")
//    private String code;
}
