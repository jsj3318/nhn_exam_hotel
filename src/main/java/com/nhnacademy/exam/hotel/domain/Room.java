package com.nhnacademy.exam.hotel.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @NotNull
    private String name;

    @NotNull
    private Byte capacity;

    @NotNull
    private Byte floor;

    @NotNull
    private boolean bathtubFlag = true;

    @NotNull
    private Byte viewType;

    @NotNull
    private LocalDateTime createdAt;
}
