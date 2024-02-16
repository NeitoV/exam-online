package com.football.pitch.data.mapper;

import com.football.pitch.data.dto.customer.CustomerCreationDTO;
import com.football.pitch.data.dto.customer.CustomerDTO;
import com.football.pitch.data.enity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(ignore = true, target = "id")
    Customer toEntity(CustomerDTO customerDTO);

    CustomerDTO toDTO(Customer customer);
}
