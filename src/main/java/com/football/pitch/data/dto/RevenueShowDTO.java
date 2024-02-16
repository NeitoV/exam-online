package com.football.pitch.data.dto;

import com.football.pitch.data.dto.manager.ManagerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueShowDTO {
    private LocationDTO locationDTO;
    private Map<TypeDTO, BigDecimal> revenues;
}
