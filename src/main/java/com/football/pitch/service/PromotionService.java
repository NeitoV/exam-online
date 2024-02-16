package com.football.pitch.service;

import com.football.pitch.data.dto.MessageResponse;
import com.football.pitch.data.dto.PromotionDTO;

import java.util.List;

public interface PromotionService {
    MessageResponse createPromotion(PromotionDTO promotionDTO);

    MessageResponse updatePromotion(long id, PromotionDTO promotionDTO);

    MessageResponse deletePromotion(long id);

    List<PromotionDTO> findAll();

    PromotionDTO findById(long id);
}
