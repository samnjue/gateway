package com.api.gateway.controller;

import com.api.gateway.entity.Hotel;
import com.api.gateway.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {
    
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel newHotel = hotelService.createHotel(hotel);
        return new ResponseEntity<>(newHotel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id)
                    .map(hotel -> new ResponseEntity<>(hotel, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public List<Hotel> searchHotels(@RequestParam String location) {
        return  hotelService.searchHotels(location);
    }
}
