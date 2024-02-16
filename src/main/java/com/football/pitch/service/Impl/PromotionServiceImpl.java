package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.MessageResponse;
import com.football.pitch.data.dto.PromotionDTO;
import com.football.pitch.data.enity.Promotion;
import com.football.pitch.data.mapper.PromotionMapper;
import com.football.pitch.data.repository.PromotionRepository;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionMapper promotionMapper;
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public MessageResponse createPromotion(PromotionDTO promotionDTO) {

        if(promotionRepository.existsByName(promotionDTO.getName())) {
            throw new ResourceNotFoundException(Collections.singletonMap("promotion name: ", promotionDTO.getName()));
        }

        Promotion promotion = promotionMapper.toEntity(promotionDTO);
        promotionRepository.save(promotion);

        return new MessageResponse(HttpServletResponse.SC_CREATED, "successfully");
    }

    @Override
    public MessageResponse updatePromotion(long id, PromotionDTO promotionDTO) {
        Promotion promotionDB = promotionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("promotion id: ", null))
        );

        if(!promotionDB.getName().equals(promotionDTO.getName())) {
            if(promotionRepository.existsByName(promotionDTO.getName())) {
                throw new ResourceNotFoundException(Collections.singletonMap("promotion name: ", promotionDTO.getName()));
            }
        }

        Promotion promotionUpdated = promotionMapper.toEntity(promotionDTO);
        promotionUpdated.setId(id);
        promotionRepository.save(promotionUpdated);

        return new MessageResponse(HttpServletResponse.SC_OK, "successfully");
    }

    @Override
    public MessageResponse deletePromotion(long id) {
        Promotion promotionDB = promotionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("promotion id: ", null))
        );
        promotionDB.setDeleted(true);

        promotionRepository.save(promotionDB);

        return new MessageResponse(HttpServletResponse.SC_OK, "successfully");
    }

    @Override
    public List<PromotionDTO> findAll() {

        return promotionRepository.findAll().stream().
                map(promotion -> promotionMapper.toDTO(promotion)).collect(Collectors.toList());
    }

    @Override
    public PromotionDTO findById(long id) {

        return promotionRepository.findById(id).map(promotion -> promotionMapper.toDTO(promotion)).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("promotion id: ", null))
        );
    }
}
