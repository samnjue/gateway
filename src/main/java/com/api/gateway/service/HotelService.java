package com.api.gateway.service;

import com.api.gateway.entity.Hotel;
import com.api.gateway.repository.HotelRepository;
import com.api.gateway.dto.HotelDTO;
import org.modelmapper.ModelMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {
    
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    private HotelDTO convertToDto(Hotel hotel) {
        return modelMapper.map(hotel, HotelDTO.class);
    }

    private Hotel convertToEntity(HotelDTO dto) {
        return modelMapper.map(dto, Hotel.class);
    }

    public HotelDTO createHotel(HotelDTO dto) { 
        Hotel hotel = convertToEntity(dto);
        Hotel savedHotel = hotelRepository.save(hotel);
        return convertToDto(savedHotel); 
    }

     public Optional<HotelDTO> getHotelById(Long id) { 
        return hotelRepository.findById(id).map(this::convertToDto);
    }

    public List<HotelDTO> searchHotels(String location) { 
        return hotelRepository.findByLocationAndAvailableRoomsGreaterThan(location, 0)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<Hotel> getHotelEntityForBooking(Long id) {
        return hotelRepository.findById(id);
    }

    public Hotel saveHotelEntity(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}
