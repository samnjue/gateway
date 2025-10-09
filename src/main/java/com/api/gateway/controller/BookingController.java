package com.api.gateway.controller;

import com.api.gateway.entity.Booking;
import com.api.gateway.service.BookingService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
        @RequestParam Long userId,
        @RequestParam(required = false) Long flightId,
        @RequestParam(required = false) Long hotelId
    ) {
        try {
            Booking newBooking = bookingService.createBooking(userId, flightId, hotelId);
            return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingByUser(@PathVariable Long userId) {
        return bookingService.getBookingByUser(userId);
    }
}
