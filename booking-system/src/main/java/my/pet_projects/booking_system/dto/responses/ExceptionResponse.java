package my.pet_projects.booking_system.dto.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {
    private String message;
    private LocalDateTime timestamp;

    public ExceptionResponse(LocalDateTime timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }
}
