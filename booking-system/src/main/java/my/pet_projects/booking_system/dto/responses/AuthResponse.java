package my.pet_projects.booking_system.dto.responses;

import lombok.Data;

@Data
public class AuthResponse {
    String token;

    public AuthResponse(String token) {
        this.token = token;
    }
}
