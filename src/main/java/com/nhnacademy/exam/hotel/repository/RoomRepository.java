package com.nhnacademy.exam.hotel.repository;

import com.nhnacademy.exam.hotel.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // 호텔 id로 룸 리스트 조회
    List<Room> findAllByHotel_HotelId(Long hotelId);

}
