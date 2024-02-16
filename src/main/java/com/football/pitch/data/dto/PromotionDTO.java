package com.football.pitch.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTO {
    private long id;
    private String name;
    private Float discountPercentage;
    private BigDecimal maxGet;
    private Date date;
    private boolean deleted;
}
