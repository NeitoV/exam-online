package com.football.pitch.data.dto.manager;

import com.football.pitch.data.dto.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerCreationDTO {
    private LoginDTO loginDTO;
    private ManagerDTO managerDTO;
}
