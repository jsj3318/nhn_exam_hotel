package com.nhnacademy.exam.hotel.dto;

public record LoginResponse(
        String accessToken,
        String tokenType,
        int expireIn
) {
}
