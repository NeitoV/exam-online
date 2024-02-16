package com.football.pitch.data.dto.customer;

import com.football.pitch.data.dto.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreationDTO {
    @NotNull(message = "TK, MK khong duoc de trong")
    private LoginDTO loginDTO;
    private CustomerDTO customerDTO;
}
