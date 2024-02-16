package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.MessageResponse;
import com.football.pitch.data.dto.PaginationDTO;
import com.football.pitch.data.dto.PriceDTO;
import com.football.pitch.data.enity.Price;
import com.football.pitch.data.mapper.PriceMapper;
import com.football.pitch.data.repository.PriceRepository;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Collections;

@Service
public class PriceServiceImpl implements PriceService {
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private PriceMapper priceMapper;


    @Override
    public MessageResponse updatePrice(long typeId, long periodId, BigDecimal pricePerHour) {
        Price price = priceRepository.findByPeriodIdAndTypeId(periodId, typeId).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("price not exists", null)));

        price.setPricePerHour(pricePerHour);

        priceRepository.save(price);

        return new MessageResponse(HttpServletResponse.SC_OK, "successfully");
    }

    @Override
    public PaginationDTO filter(int pageNumber, int pageSize, Long typeId, Long periodId) {
        Page<PriceDTO> page = priceRepository.filterByTypeAndPeriod(PageRequest.of(pageNumber, pageSize), typeId, periodId)
                .map(price -> priceMapper.toDTO(price));

        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(),
                page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize());
    }


    @Override
    public PriceDTO findById(long id) {

        return priceRepository.findById(id).map(price -> priceMapper.toDTO(price)).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("price id: ", id))
        );
    }
}
