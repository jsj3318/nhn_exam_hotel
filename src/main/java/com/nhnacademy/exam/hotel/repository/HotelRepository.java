package com.nhnacademy.exam.hotel.repository;

import com.nhnacademy.exam.hotel.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
