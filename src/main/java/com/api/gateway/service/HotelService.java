package com.api.gateway.service;

import com.api.gateway.entity.Hotel;
import com.api.gateway.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {
    
    private final HotelRepository hotelRepository;

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    public List<Hotel> searchHotels(String location) {
        return hotelRepository.findByLocationAndAvailableRoomsGreaterThan(location, 0);
    }
}
