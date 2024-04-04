package com.exam.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShowDTO {
    private long id;
    private String email;
    private String name;
    private RoleDTO roleDTO;
}
