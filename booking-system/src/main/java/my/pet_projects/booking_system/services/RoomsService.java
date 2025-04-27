package my.pet_projects.booking_system.services;

import my.pet_projects.booking_system.dto.requests.RoomRequest;
import my.pet_projects.booking_system.dto.responses.RoomResponse;
import my.pet_projects.booking_system.models.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomsService {
    RoomResponse createRoom(RoomRequest roomRequest);
    Room  findRoomByName(String roomName);
    List<RoomResponse> findAllRooms();
    RoomResponse findRoomById(Long id);
    RoomResponse updateRoom(Long id, RoomRequest roomRequest);
    void deleteRoomById(Long id);
    List<RoomResponse> getAvailableRooms(int capacity, LocalDateTime start, LocalDateTime end);
}
