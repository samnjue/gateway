package com.api.gateway.service;

import com.api.gateway.entity.Flight;
import com.api.gateway.exception.ResourceNotFoundException;
import com.api.gateway.repository.FlightRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.api.gateway.dto.FlightDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {
    
    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;

    private FlightDTO convertToDto(Flight flight) {
        return modelMapper.map(flight, FlightDTO.class);
    }

    private Flight convertToEntity(FlightDTO dto) {
        return modelMapper.map(dto, Flight.class);
    }

    public FlightDTO createFlight(FlightDTO dto) {
        Flight flight = convertToEntity(dto);
        Flight savedFlight = flightRepository.save(flight);
        return convertToDto(savedFlight);
    }

    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Flight with id " + id + " not found"));
        return convertToDto(flight);
    }

    public List<FlightDTO> searchFlights(String origin) {
        return flightRepository.findByOriginAndAvailableSeatsGreaterThan(origin, 0)
                            .stream()
                            .map(this::convertToDto)
                            .collect(Collectors.toList());
    }

    @Transactional
    public Optional<Flight> getFlightEntityForBooking(Long id) {
        return flightRepository.findById(id);
    }

    public Flight saveFlightEntity(Flight flight) {
        return flightRepository.save(flight);
    }
}
