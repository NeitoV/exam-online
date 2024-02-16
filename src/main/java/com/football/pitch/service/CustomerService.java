package com.football.pitch.service;

import com.football.pitch.data.dto.MessageResponse;
import com.football.pitch.data.dto.customer.CustomerCreationDTO;
import com.football.pitch.data.dto.customer.CustomerDTO;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerService {
    @Transactional
    MessageResponse createCustomer(CustomerCreationDTO customerCreationDTO);

    @Transactional
    MessageResponse updateCustomer(long id, CustomerDTO customerDTO);

    CustomerDTO getInfoCustomer();

    CustomerDTO getById(long id);
}
