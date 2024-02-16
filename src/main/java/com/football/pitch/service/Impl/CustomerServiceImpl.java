package com.football.pitch.service.Impl;

import com.football.pitch.data.dto.MessageResponse;
import com.football.pitch.data.dto.customer.CustomerCreationDTO;
import com.football.pitch.data.dto.customer.CustomerDTO;
import com.football.pitch.data.enity.Customer;
import com.football.pitch.data.enity.User;
import com.football.pitch.data.mapper.CustomerMapper;
import com.football.pitch.data.repository.UserRepository;
import com.football.pitch.enumeration.ERole;
import com.football.pitch.data.repository.CustomerRepository;
import com.football.pitch.exception.AccessDeniedException;
import com.football.pitch.exception.ResourceNotFoundException;
import com.football.pitch.service.CustomerService;
import com.football.pitch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public MessageResponse createCustomer(CustomerCreationDTO customerCreationDTO) {
        Customer customer = customerMapper.toEntity(customerCreationDTO.getCustomerDTO());

        User user = userService.createUser(customerCreationDTO.getLoginDTO(), ERole.roleCustomer);
        customer.setUser(user);
        customerRepository.save(customer);

        return new MessageResponse(HttpServletResponse.SC_CREATED, "successfully");
    }

    @Override
    public MessageResponse updateCustomer(long id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("customer id: ", id))
        );

        Customer customerUpdated = customerMapper.toEntity(customerDTO);
        customerUpdated.setId(id);
        customerUpdated.setUser(customer.getUser());

        customerRepository.save(customerUpdated);

        return new MessageResponse(HttpServletResponse.SC_OK, "successfully");
    }

    @Override
    public CustomerDTO getInfoCustomer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AccessDeniedException(Collections.singletonMap("email: ", email))
        );

        Customer customer = customerRepository.findByUserId(user.getId()).orElseThrow(
                () -> new AccessDeniedException(Collections.singletonMap("user has been delete", user.getId()))
        );

        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO getById(long id) {

        return customerRepository.findById(id).map(customer -> customerMapper.toDTO(customer)).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("customer id: ", id))
        );
    }

}
