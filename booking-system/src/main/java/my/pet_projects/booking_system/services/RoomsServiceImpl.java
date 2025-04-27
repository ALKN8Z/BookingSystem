package my.pet_projects.booking_system.services;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.pet_projects.booking_system.dto.requests.RoomRequest;
import my.pet_projects.booking_system.dto.responses.RoomResponse;
import my.pet_projects.booking_system.exceptions.ResourceNotFoundException;
import my.pet_projects.booking_system.models.Room;
import my.pet_projects.booking_system.repositories.RoomsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomsServiceImpl implements RoomsService {

    private final ModelMapper modelMapper;
    private final RoomsRepository roomsRepository;

    @Transactional
    @Override
    public RoomResponse createRoom(RoomRequest roomRequest) {
        Room newRoom = modelMapper.map(roomRequest, Room.class);
        return modelMapper.map(roomsRepository.save(newRoom), RoomResponse.class);
    }

    @Override
    public Room findRoomByName(String roomName) {
        return roomsRepository.findByName(roomName);
    }

    @Override
    public List<RoomResponse> findAllRooms() {
        return roomsRepository.findAll().stream().map(room ->
                modelMapper.map(room, RoomResponse.class)).collect(Collectors.toList());
    }

    @Override
    public RoomResponse findRoomById(Long id) {
        Room room = roomsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Комната с таким id не найдена"));

        return modelMapper.map(room, RoomResponse.class);
    }

    @Override
    @Transactional
    public RoomResponse updateRoom(Long id, RoomRequest roomRequest) {
        Room roomToBeUpdated = roomsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Комната с таким id не найдена"));

        roomToBeUpdated.setName(roomRequest.getName());
        roomToBeUpdated.setCapacity(roomRequest.getCapacity());

        return modelMapper.map(roomsRepository.save(roomToBeUpdated), RoomResponse.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long id) {
        Room roomToBeDeleted = roomsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Комната с таким id не найдена")
        );
        roomsRepository.delete(roomToBeDeleted);
    }

    @Override
    public List<RoomResponse> getAvailableRooms(int capacity, LocalDateTime start, LocalDateTime end) {

        validateParameters(start, end);

        return roomsRepository.findAvailableRooms(capacity, start, end).stream().map(
                room -> modelMapper.map(room, RoomResponse.class))
                .collect(Collectors.toList());
    }




    private void validateParameters(LocalDateTime start, LocalDateTime end) {
        if (start.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Время начала не может быть в прошлом");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Конец бронирования должен быть после начала");
        }
    }

}
