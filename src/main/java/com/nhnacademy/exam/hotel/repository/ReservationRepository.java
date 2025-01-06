package com.nhnacademy.exam.hotel.repository;

import com.nhnacademy.exam.hotel.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // datetime과 roomId를 받아서, 해당 날짜에 해당 방에 예약이 있는지 여부
    Boolean existsByRoom_RoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(Long roomId, LocalDate checkIn, LocalDate checkOut);

    // 날짜, 사용자 아이디를 받아서, 해당 사용자가 해당 날짜에 몇개의 방을 예약중인지 확인
    int countByUserIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(Long userId, LocalDate checkIn, LocalDate checkOut);

}
