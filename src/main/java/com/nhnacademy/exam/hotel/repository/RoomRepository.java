package com.nhnacademy.exam.hotel.repository;

import com.nhnacademy.exam.hotel.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // 호텔 id로 룸 리스트 조회
    List<Room> findAllByHotel_HotelId(Long hotelId);

    // 호텔에서 객실 이름이 존재하는지 확인
    Boolean existsByHotel_HotelIdAndName(Long hotelId, String name);

    // 호텔 아이디와 방 이름으로 방 조회
    Room findByHotel_HotelIdAndName(Long hotelId, String name);

}
