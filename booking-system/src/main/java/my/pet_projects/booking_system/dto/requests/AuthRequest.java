package my.pet_projects.booking_system.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Введенный email невалиден")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
