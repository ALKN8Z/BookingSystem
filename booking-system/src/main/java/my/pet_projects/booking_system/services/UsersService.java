package my.pet_projects.booking_system.services;

import my.pet_projects.booking_system.models.User;

import java.util.Optional;

public interface UsersService {
    Optional<User> findUserByEmail(String email);
    Boolean userExists(String email);
    void saveUser(User user);
    Optional<User> findUserById(Long userId);
}
