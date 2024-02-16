package com.football.pitch.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShowDTO {
    private long id;
    private String fullName;
    private String phoneNumber;
    private Date birthday;
    private String email;
    private RoleDTO roleDTO;
}
