package com.nhnacademy.exam.hotel.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationRequest(
        LocalDate checkIn,
        LocalDate checkOut,
        int peopleCount,
        String roomName
) {
}
