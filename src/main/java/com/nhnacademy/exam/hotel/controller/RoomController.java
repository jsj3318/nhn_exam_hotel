package com.nhnacademy.exam.hotel.controller;

import com.nhnacademy.exam.hotel.dto.RoomResponse;
import com.nhnacademy.exam.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // 인증 테스트
    @GetMapping("/")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().build();
    }

    // 객실 조회
    @GetMapping("/v1/hotel-api/hotels/{hotel-id}/rooms")
    public List<RoomResponse> getAllRooms(
            @PathVariable("hotel-id") Long hotelId
    ) {
        return roomService.getRoomsByHotelId(hotelId);
    }

}