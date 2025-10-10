package com.api.gateway.service;

import com.api.gateway.entity.Booking;
import com.api.gateway.entity.Flight;
import com.api.gateway.entity.Hotel;
import com.api.gateway.entity.User;
import com.api.gateway.exception.ResourceNotFoundException;
import com.api.gateway.repository.BookingRepository;
import com.api.gateway.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightService flightService;
    private final HotelService hotelService;

    @Transactional
    public Booking createBooking(Long userId, Long flightId, Long hotelId) {

        User user  =userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setStatus("PENDING");
        double totalPrice = 0.0;

        if (flightId != null) {
            Flight flight = flightService.getFlightEntityForBooking(flightId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Flight with id " + flightId + " not found"));

            if (flight.getAvailableSeats() < 1) {
                throw new RuntimeException("No available seats on flight " + flight.getFlightNumber());
            } 

            flight.setAvailableSeats(flight.getAvailableSeats() - 1);
            flightService.saveFlightEntity(flight);

            booking.setFlight(flight);
            totalPrice += flight.getPrice();
        }

        if (hotelId != null) {
            Hotel hotel = hotelService.getHotelEntityForBooking(hotelId)
                                .orElseThrow(() -> new ResourceNotFoundException("Hotel with ID " + hotelId + " not found"));

            if (hotel.getAvailableRooms() < 1) {
                throw new RuntimeException("No available rooms at hotel " + hotel.getName());
            }
            
            hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
            hotelService.saveHotelEntity(hotel);

            booking.setHotel(hotel);
            totalPrice += hotel.getPricePerNight();
        }

        if (flightId == null && hotelId == null) {
            throw new RuntimeException("A booking must include at least one flight and hotel");
        }

        booking.setTotalPrice(totalPrice);
        booking.setStatus("CONFIRMED");

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}
