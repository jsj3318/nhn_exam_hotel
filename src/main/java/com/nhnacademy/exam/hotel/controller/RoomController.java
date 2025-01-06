package com.nhnacademy.exam.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {

    @GetMapping("/")
    public ResponseEntity<?> index() {
        return ResponseEntity.ok().build();
    }

}