package com.football.pitch.data.dto.pitch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PitchCreationDTO {
    private Long managerId;
    private PitchDTO pitchDTO;
}
