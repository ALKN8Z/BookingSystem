package my.pet_projects.booking_system.dto.responses;

import lombok.Data;
import my.pet_projects.booking_system.models.BookingStatus;
import my.pet_projects.booking_system.models.Room;
import my.pet_projects.booking_system.models.User;

import java.time.LocalDateTime;

@Data
public class BookingResponse {
    private int id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private BookingStatus status;

    private boolean notified;

    private UserResponse user;

    private RoomResponse room;
}
