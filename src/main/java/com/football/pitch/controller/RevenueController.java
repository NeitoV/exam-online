package com.football.pitch.controller;

import com.football.pitch.data.dto.PromotionDTO;
import com.football.pitch.data.enity.Manager;
import com.football.pitch.service.ManagerService;
import com.football.pitch.service.RevenueService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/revenue")
public class RevenueController {
    @Autowired
    private RevenueService revenueService;
    @Autowired
    private ManagerService managerService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Admin')")
    @GetMapping("")
    ResponseEntity<?> show(@RequestParam String monthOfYear, @RequestParam(required = false) Integer day,
                           @RequestParam(defaultValue = "0") int pageNumber,
                           @RequestParam(defaultValue = "10") int pageSize) {

        return ResponseEntity.ok(revenueService.showRevenue(monthOfYear, day, pageNumber, pageSize));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Manager')")
    @GetMapping("/manager")
    ResponseEntity<?> showForManager(@RequestParam String monthOfYear, @RequestParam(required = false) Integer day) {
        Manager manager = managerService.getManagerFromToken();

        return ResponseEntity.ok(revenueService.showRevenueForManager(manager.getId(), monthOfYear, day));
    }
}
