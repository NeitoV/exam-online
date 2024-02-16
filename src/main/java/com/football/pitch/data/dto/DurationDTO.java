package com.football.pitch.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DurationDTO {
    private long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private PeriodDTO periodDTO;
}
