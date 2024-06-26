package com.exam.data.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class RegisterDTO {
    @Email(message = "email khong dung dinh dang")
    @NotNull(message = "email khong duoc de trong")
    private String email;

    @NotNull(message = "password khong duoc de trong")
    private String password;

    @NotNull(message = "passwordConfirm khong duoc de trong")
    private String passwordConfirm;

    @NotNull(message = "name khong duoc de trong")
    private String name;

    @NotNull(message = "Ban nen nho chon Role")
    private long roleId;
}
