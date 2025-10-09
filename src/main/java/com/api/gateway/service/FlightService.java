package com.api.gateway.service;

import com.api.gateway.entity.Flight;
import com.api.gateway.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightService {
    
    private final FlightRepository flightRepository;

    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    public List<Flight> searchFlights(String origin) {
        return flightRepository.findByOriginAndAvailableSeatsGreaterThan(origin, 0);
    }
}
