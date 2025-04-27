package my.pet_projects.booking_system.dto.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingRequest {
    @NotNull(message = "Время начала бронирования не может быть пустым")
    private LocalDateTime startTime;

    @NotNull(message = "Время окончания бронирования не может быть пустым")
    private LocalDateTime endTime;

    @NotNull(message = "Id комнаты не может быть пустым")
    private Long roomId;

}
