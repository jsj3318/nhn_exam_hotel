package com.nhnacademy.exam.hotel.service;

import com.nhnacademy.exam.hotel.domain.Hotel;
import com.nhnacademy.exam.hotel.domain.Reservation;
import com.nhnacademy.exam.hotel.domain.Room;
import com.nhnacademy.exam.hotel.domain.ViewType;
import com.nhnacademy.exam.hotel.dto.ReservationRequest;
import com.nhnacademy.exam.hotel.dto.ReservationResponse;
import com.nhnacademy.exam.hotel.dto.RoomRequest;
import com.nhnacademy.exam.hotel.dto.RoomResponse;
import com.nhnacademy.exam.hotel.exception.DataAlreadyExistsException;
import com.nhnacademy.exam.hotel.exception.InvalidAccessException;
import com.nhnacademy.exam.hotel.exception.WrongDataException;
import com.nhnacademy.exam.hotel.formatter.TimeFormatter;
import com.nhnacademy.exam.hotel.repository.HotelRepository;
import com.nhnacademy.exam.hotel.repository.ReservationRepository;
import com.nhnacademy.exam.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    private final TimeFormatter timeFormatter;

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
                            timeFormatter.convert(room.getCreatedAt())
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

    @Transactional
    public ReservationResponse reserveRoom(Long userId, Long hotelId, ReservationRequest request){
        // 데이터 검증
        if(request.peopleCount() < 1) {
            throw new WrongDataException("인원은 1명 이상이어야 합니다.");
        }

        if(request.checkIn().isAfter(request.checkOut())){
            throw new WrongDataException("체크인이 체크아웃보다 나중일 수 없습니다.");
        }

        // 호텔 아이디 검사
        if(hotelRepository.findById(hotelId).isEmpty()){
            throw new WrongDataException("No Hotel : " + hotelId);
        }

        // 호텔 아이디와 방 이름으로 방 조회
        Room room = roomRepository.findByHotel_HotelIdAndName(hotelId, request.roomName());
        if(room == null){
            throw new WrongDataException("No Room : " + request.roomName());
        }

        // 객실과 날짜 관련 해서 검사하기 위한 반복
        for(LocalDate dateTime = request.checkIn();     // 신청한 체크인 날짜 부터
            !dateTime.isAfter(request.checkOut());   // 체크아웃 날짜까지
            dateTime = dateTime.plusDays(1)){   // 하루씩 더한다

            // 같은 날짜에 한 객실에 여러명 예약 불가능
            // 예약 중에서 같은 방에 대하여, 현재 루프의 dateTime이
            // 체크인과 체크아웃 사이에 포함된 예약이 있는지 검사한다
            if(reservationRepository.existsByRoom_RoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                    room.getRoomId(),
                    dateTime,
                    dateTime
            )){
                throw new DataAlreadyExistsException("reservation already exists in " + dateTime);
            }

            // 사용자는 하루에 최대 3개의 객실만 예약 가능
            // 현재 루프의 날짜에서 현재 신청한 사용자가 예약중인 방이 몇개인지 확인한다.
            int count = reservationRepository.countByUserIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                    userId,
                    dateTime,
                    dateTime
            );
            if(count == 3){
                throw new InvalidAccessException(dateTime + " 해당 날짜에 더 이상 예약 할 수 없습니다.");
            }
        }

        // 예약 정보 저장
        Reservation reservation = Reservation.builder()
                .userId(userId)
                .checkIn(request.checkIn())
                .checkOut(request.checkOut())
                .peopleCount(request.peopleCount())
                .room(room)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);

        // 저장한 객체 불러와서 반환
        ReservationResponse response = new ReservationResponse(
                savedReservation.getReservationId(),
                savedReservation.getCheckIn(),
                savedReservation.getCheckOut(),
                savedReservation.getPeopleCount(),
                hotelId,
                savedReservation.getRoom().getName()
        );

        return response;
    }

}
