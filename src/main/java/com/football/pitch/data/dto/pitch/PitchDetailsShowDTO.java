package com.football.pitch.data.dto.pitch;

import com.football.pitch.data.dto.DurationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PitchDetailsShowDTO {
    private Long id;
    private PitchDTO pitchDTO;
    private DurationDTO durationDTO;
    private BigDecimal pricePerHour;
}
