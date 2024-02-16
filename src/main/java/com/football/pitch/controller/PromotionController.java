package com.football.pitch.controller;

import com.football.pitch.data.dto.PromotionDTO;
import com.football.pitch.service.PromotionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PostMapping("")
    ResponseEntity<?> createPromotion(@RequestBody PromotionDTO promotionDTO) {

        return new ResponseEntity<>(promotionService.createPromotion(promotionDTO), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @PutMapping("/{id}")
    ResponseEntity<?> updatePromotion(@RequestBody PromotionDTO promotionDTO, @PathVariable Long id) {

        return ResponseEntity.ok(promotionService.updatePromotion(id, promotionDTO));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePromotion(@PathVariable Long id) {

        return ResponseEntity.ok(promotionService.deletePromotion(id));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findPromotionById(@PathVariable Long id) {

        return ResponseEntity.ok(promotionService.findById(id));
    }

    @GetMapping("")
    ResponseEntity<?> findAllPromotion() {

        return ResponseEntity.ok(promotionService.findAll());
    }
}
