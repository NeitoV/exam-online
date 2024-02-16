package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.PriceDTO;
import com.football.pitch.data.enity.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TypeMapper.class})
public interface PriceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "period.id", source = "periodId")
    @Mapping(target = "type", source = "typeDTO" )
    Price toEntity(PriceDTO priceDTO);

    @Mapping(source = "period.id",target = "periodId")
    @Mapping(source = "type", target = "typeDTO" )
    PriceDTO toDTO(Price price);
}
