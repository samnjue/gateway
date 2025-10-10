package com.api.gateway.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequestDTO {

    @NotNull(message = "User ID is required for booking")
    private Long userId;

    private Long flightId;
    private Long hotelId;
}