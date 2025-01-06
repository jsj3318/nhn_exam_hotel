package com.nhnacademy.exam.hotel.service;

import com.nhnacademy.exam.hotel.domain.Hotel;
import com.nhnacademy.exam.hotel.domain.Room;
import com.nhnacademy.exam.hotel.domain.ViewType;
import com.nhnacademy.exam.hotel.dto.RoomRequest;
import com.nhnacademy.exam.hotel.dto.RoomResponse;
import com.nhnacademy.exam.hotel.exception.DataAlreadyExistsException;
import com.nhnacademy.exam.hotel.exception.WrongDataException;
import com.nhnacademy.exam.hotel.repository.HotelRepository;
import com.nhnacademy.exam.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    // RoomResponse 클래스는 Room Entity 객체를 클라이언트에게 응답하기 위한 DTO 입니다.
    // 객실 정보 조회 API 명세서의 Response 양식을 보시고 적절한 형태로 RoomResponse 클래스를 만들어주세요.
    // JSON message 의 viewType 속성은 미리 제공한 ViewType enum의 parameter 값을 사용해야 합니다.
    // Hint. javax.persistence.AttributeConverter 인터페이스와 @Convert 애너테이션을 사용하면 됩니다.

    public List<RoomResponse> getRoomsByHotelId(Long hotelId){
        if(hotelRepository.findById(hotelId).isEmpty()){
            throw new WrongDataException("No Hotel : " + hotelId);
        }

        List<Room> roomList = roomRepository.findAllByHotel_HotelId(hotelId);

        // entity -> dto
        List<RoomResponse> roomResponseList = new ArrayList<>();
        for (Room room : roomList) {
            roomResponseList.add(
                    new RoomResponse(
                            room.getRoomId(),
                            room.getName(),
                            room.getCapacity(),
                            room.getFloor(),
                            room.isBathtubFlag(),
                            room.getViewType(),
                            room.getCreatedAt()
                    )
            );
        }

        return roomResponseList;
    }

    @Transactional
    public Long addRoom(Long hotelId, RoomRequest request){
        // 한 호텔에는 동일한 방 이름이 없다
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                () -> new WrongDataException("No Hotel : " + hotelId)
        );

        if(roomRepository.existsByHotel_HotelIdAndName(hotelId, request.name())){
            throw new DataAlreadyExistsException("room name already exists : " + request.name());
        }

        Room room = Room.builder()
                .name(request.name())
                .hotel(hotel)
                .capacity(request.capacity())
                .floor(request.floor())
                .bathtubFlag(request.hasBathtub())
                .viewType(ViewType.fromParameter(request.viewType()))
                .createdAt(LocalDateTime.now())
                .build();

        return roomRepository.save(room).getRoomId();
    }

}
