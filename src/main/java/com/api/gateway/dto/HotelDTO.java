package com.api.gateway.dto;

import lombok.Data;

@Data
public class HotelDTO {
    
    private Long id;
    private String name;
    private String location;
    private double pricePerNight;
    private int availableRooms;
}
