package com.football.pitch.controller;

import com.football.pitch.data.dto.customer.CustomerCreationDTO;
import com.football.pitch.data.dto.customer.CustomerDTO;
import com.football.pitch.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerCreationDTO customerCreationDTO) {

        return new ResponseEntity<>(customerService.createCustomer(customerCreationDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {

        return ResponseEntity.ok(customerService.updateCustomer(id, customerDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Customer')")
    @GetMapping("/info")
    ResponseEntity<?> getInfoCustomerByToken() {

        return ResponseEntity.ok(customerService.getInfoCustomer());
    }

    @GetMapping("/{id}")
    ResponseEntity<?> updateCustomer(@PathVariable Long id) {

        return ResponseEntity.ok(customerService.getById(id));
    }
}
