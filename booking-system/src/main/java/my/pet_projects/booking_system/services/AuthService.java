package my.pet_projects.booking_system.services;

import my.pet_projects.booking_system.dto.requests.AuthRequest;

public interface AuthService {
    String login(AuthRequest authRequest);
}
