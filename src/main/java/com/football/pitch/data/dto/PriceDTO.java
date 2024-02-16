package com.football.pitch.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDTO {
    private long id;
    private long periodId;
    private TypeDTO typeDTO;
    private BigDecimal pricePerHour;
}
