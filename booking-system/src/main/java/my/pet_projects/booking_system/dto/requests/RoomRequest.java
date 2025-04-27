package my.pet_projects.booking_system.dto.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RoomRequest {

    @NotBlank(message = "Название комнаты не может быть пустым")
    private String name;

    @NotNull(message = "Вместимость комнаты обязательна")
    @Positive(message = "Вместимость комнаты должна быть положительной")
    private Long capacity;
}
