package com.nhnacademy.exam.hotel.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record ReservationResponse(
        Long userId,
        LocalDate checkIn,
        LocalDate checkOut,
        int peopleCount,
        Long hotelId,
        String roomName
        ) {

}
