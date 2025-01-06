package com.nhnacademy.exam.hotel.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RoomRequest(
        @NotNull
        @Length(max = 100)
        String name,

        @NotNull
        Byte capacity,

        @NotNull
        Byte floor,

        Boolean hasBathtub,

        @NotNull
        String viewType
) {
}
