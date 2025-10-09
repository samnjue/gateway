package com.api.gateway.controller;

import com.api.gateway.entity.Flight;
import com.api.gateway.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
public class FlightController {
    
    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight newFlight = flightService.createFlight(flight);
        return new ResponseEntity<>(newFlight, HttpStatus.CREATED);
    }   

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightByid(@PathVariable Long id) {
        return flightService.getFlightById(id)
                    .map(flight -> new ResponseEntity<>(flight, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public List<Flight> searchFlights(@RequestParam String origin) {
        return flightService.searchFlights(origin);
    }
}
