package my.pet_projects.booking_system.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.pet_projects.booking_system.dto.requests.AuthRequest;
import my.pet_projects.booking_system.dto.responses.AuthResponse;
import my.pet_projects.booking_system.exceptions.ConflictException;
import my.pet_projects.booking_system.models.User;
import my.pet_projects.booking_system.services.AuthService;
import my.pet_projects.booking_system.services.NotificationService;
import my.pet_projects.booking_system.services.UsersService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsersService usersService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AuthRequest authRequest) {
        if (usersService.userExists(authRequest.getEmail())) {
            throw new ConflictException("Введенный Email уже занят ");
        }
        else{
            User newUser = new User();
            newUser.setEmail(authRequest.getEmail());
            newUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
            newUser.setRoles(Set.of("ROLE_USER"));

            usersService.saveUser(newUser);
            notificationService.sendWelcomeNotification(newUser);
            return ResponseEntity.ok("Регистрация прошла успешно");
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        String token = authService.login(authRequest);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).body(new AuthResponse(token));
    }
}
