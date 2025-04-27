package my.pet_projects.booking_system.dto.responses;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private Set<String> roles = new HashSet<>();
}
