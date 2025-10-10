package com.api.gateway.controller;

//import com.api.gateway.entity.Flight;
import com.api.gateway.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.gateway.dto.FlightDTO;   
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
public class FlightController {
    
    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightDTO> createFlight(@Valid @RequestBody FlightDTO dto) {
        FlightDTO newFlight = flightService.createFlight(dto);
        return new ResponseEntity<>(newFlight, HttpStatus.CREATED);
    }   

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightByid(@PathVariable Long id) {
        FlightDTO dto = flightService.getFlightById(id);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public List<FlightDTO> searchFlights(@RequestParam String origin) {
        return flightService.searchFlights(origin);
    }
}
