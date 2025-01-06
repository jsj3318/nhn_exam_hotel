package com.nhnacademy.exam.hotel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.exam.hotel.domain.ViewType;

public record RoomResponse(
        @JsonProperty("id")
        Long roomId,

        String name,

        Byte capacity,

        Byte floor,

        @JsonProperty("hasBathtub")
        boolean bathtubFlag,

        ViewType viewType,

        String createdAt
) {
}
