package com.football.pitch.service;

import com.football.pitch.data.dto.MessageResponse;
import com.football.pitch.data.dto.PaginationDTO;
import com.football.pitch.data.dto.PriceDTO;

import java.math.BigDecimal;

public interface PriceService {
    MessageResponse updatePrice(long typeId, long periodId, BigDecimal pricePerHour);

    PaginationDTO filter(int pageNumber, int pageSize, Long typeId, Long periodId);

    PriceDTO findById(long id);
}
