package my.pet_projects.booking_system.services.impl;

import lombok.RequiredArgsConstructor;
import my.pet_projects.booking_system.dto.requests.AuthRequest;
import my.pet_projects.booking_system.security.JwtUtils;
import my.pet_projects.booking_system.security.MyUserDetails;
import my.pet_projects.booking_system.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public String login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), authRequest.getPassword()));

        return jwtUtils.generateToken((MyUserDetails) authentication.getPrincipal());
    }
}
