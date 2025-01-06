package com.nhnacademy.exam.hotel.controller;

import com.nhnacademy.exam.hotel.dto.RoomCreateResponse;
import com.nhnacademy.exam.hotel.dto.RoomRequest;
import com.nhnacademy.exam.hotel.dto.RoomResponse;
import com.nhnacademy.exam.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 객실 생성
    @PostMapping("/v1/hotel-api/hotels/{hotel-id}/rooms")
    public ResponseEntity<?> createRoom(
            @PathVariable("hotel-id") Long hotelId,
            @RequestBody RoomRequest request
            ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new RoomCreateResponse(roomService.addRoom(hotelId, request))
                );
    }

}