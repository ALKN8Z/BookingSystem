package my.pet_projects.booking_system.security;

import lombok.RequiredArgsConstructor;
import my.pet_projects.booking_system.models.User;
import my.pet_projects.booking_system.services.UsersService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = usersService.findUserByEmail(email);

        if (user.isPresent()) {
            return new MyUserDetails(user.get());
        }
        else{
            throw new UsernameNotFoundException("Пользователь с email - " + email + ", не найден");
        }
    }

}
