package my.pet_projects.booking_system.dto.responses;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RoomResponse {
    private Long id;
    private String name;
    private Long capacity;
}
