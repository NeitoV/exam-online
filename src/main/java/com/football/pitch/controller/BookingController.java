package com.football.pitch.controller;

import com.football.pitch.data.dto.booking.BookingCreationDTO;
import com.football.pitch.service.BookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Customer')")
    @PostMapping(value ="")
    public ResponseEntity<?> createdBooking( @RequestBody BookingCreationDTO bookingCreationDTO) {

        return new ResponseEntity<>(bookingService.createBooking(bookingCreationDTO),
                HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable long bookingId) {

        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('Role_Admin', 'Role_Manager')")
    @GetMapping("/filter")
    public ResponseEntity<?> filterBooking(@RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "10") int pageSize,
                                           @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                                           @RequestParam(required = false) Long typeId,
                                           @RequestParam(required = false) Long durationId,
                                           @RequestParam(required = false) Long statusId) {

        return ResponseEntity.ok(bookingService.filterBooking(date, typeId, durationId, statusId, pageNumber, pageSize));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> findBookingById(@PathVariable long id) {

        return ResponseEntity.ok(bookingService.findById(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Customer')")
    @GetMapping("/history")
    public ResponseEntity<?> getHistoryBooking() {

        return ResponseEntity.ok(bookingService.findHistoryBooking());
    }
}
