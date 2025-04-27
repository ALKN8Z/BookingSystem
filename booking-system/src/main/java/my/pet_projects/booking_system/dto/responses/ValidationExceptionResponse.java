package my.pet_projects.booking_system.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationExceptionResponse {
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;
}
