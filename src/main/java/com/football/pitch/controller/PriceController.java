package com.football.pitch.controller;

import com.football.pitch.service.PriceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/price")
public class PriceController {
    @Autowired
    private PriceService priceService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PutMapping("/")
    ResponseEntity<?> updatePrice(@RequestParam long periodId, @RequestParam long typeId,
                                  @RequestParam BigDecimal pricePerHour) {

        return ResponseEntity.ok(priceService.updatePrice(typeId, periodId, pricePerHour));
    }


    @GetMapping("/{id}")
    ResponseEntity<?> findPriceById(@PathVariable Long id) {

        return ResponseEntity.ok(priceService.findById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterPrice(@RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(required = false) Long typeId,
                                        @RequestParam(required = false) Long periodId) {

        return ResponseEntity.ok(priceService.filter(pageNumber, pageSize, typeId, periodId));
    }
}
