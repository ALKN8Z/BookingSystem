package my.pet_projects.booking_system.controllers;


import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import my.pet_projects.booking_system.dto.requests.RoomRequest;
import my.pet_projects.booking_system.dto.responses.RoomResponse;
import my.pet_projects.booking_system.services.RoomsService;
import my.pet_projects.booking_system.utils.RoomValidator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomsService roomsService;
    private final RoomValidator roomValidator;

    @PostMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse createRoom(
            @RequestBody @Valid RoomRequest roomRequest,
            BindingResult bindingResult) throws MethodArgumentNotValidException {

        roomValidator.validate(roomRequest, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        return roomsService.createRoom(roomRequest);
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getRooms(){
        return ResponseEntity.ok(roomsService.findAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long id){
        return ResponseEntity.ok(roomsService.findRoomById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @RequestBody @Valid RoomRequest roomRequest){
        return ResponseEntity.ok(roomsService.updateRoom(id, roomRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id){
        roomsService.deleteRoomById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms(
            @RequestParam int capacity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime
    ){
        return ResponseEntity.ok().body(roomsService.getAvailableRooms(capacity, startTime, endTime));
    }
}
