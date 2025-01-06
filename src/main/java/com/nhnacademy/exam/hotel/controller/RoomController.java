package com.nhnacademy.exam.hotel.controller;

import com.nhnacademy.exam.hotel.dto.*;
import com.nhnacademy.exam.hotel.exception.InvalidAccessException;
import com.nhnacademy.exam.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    // 예약 요청
    @PostMapping("/v1/hotel-api/hotels/{hotel-id}/reservation")
    public ResponseEntity<?> reserveRoom(
            @PathVariable("hotel-id") Long hotelId,
            @RequestBody ReservationRequest request,
            Authentication authentication
    ) {
        // 헤더 토큰 파싱 -> 인증 정보에 저장
        // userId를 인증 정보에서 가져온다
        Integer principal = (Integer) authentication.getPrincipal();
        Long userId = Long.valueOf(principal);

        // 예약 가능한 사용자인지 검증
        if(!UserAuthzValidator.isValid(userId)) {
            throw new InvalidAccessException("예약할 수 없는 사용자 입니다. : " + userId);
        }

        ReservationResponse response = roomService.reserveRoom(hotelId, userId, request);

        return ResponseEntity.ok(response);
    }

}