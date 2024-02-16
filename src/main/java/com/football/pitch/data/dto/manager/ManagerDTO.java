package com.football.pitch.data.dto.manager;

import com.football.pitch.data.dto.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO {
    private Long id;
    private Long userId;
    private String fullName;
    private String phoneNumber;
    private Date birthday;
    private LocationDTO locationDTO;
}
