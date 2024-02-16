package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.PromotionDTO;
import com.football.pitch.data.enity.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    @Mapping(target = "id", ignore = true)
    Promotion toEntity(PromotionDTO promotionDTO);

    PromotionDTO toDTO(Promotion promotion);
}
