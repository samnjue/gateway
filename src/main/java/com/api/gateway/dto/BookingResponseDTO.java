package com.api.gateway.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {
    private Long id;

    private UserDTO user;
    private FlightDTO flight;
    private HotelDTO hotel;

    private String status;
    private double totalPrice;
    private LocalDateTime bookingDate;
}
