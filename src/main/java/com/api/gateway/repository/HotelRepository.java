package com.api.gateway.repository;

import com.api.gateway.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByLocationAndAvailableRoomsGreaterThan(String location, int minRooms);
}
