package com.nhnacademy.exam.hotel.repository;

import com.nhnacademy.exam.hotel.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
