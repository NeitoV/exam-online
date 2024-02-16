package com.football.pitch.service;

import com.football.pitch.data.dto.PaginationDTO;
import com.football.pitch.data.dto.RevenueShowDTO;
import com.football.pitch.data.dto.TypeDTO;

import java.math.BigDecimal;
import java.util.Map;

public interface RevenueService {
    PaginationDTO showRevenue(String monthOfDate, Integer day, int pageNumber, int pageSize);


    RevenueShowDTO showRevenueForManager(long managerId, String monthOfDate, Integer day);
}
