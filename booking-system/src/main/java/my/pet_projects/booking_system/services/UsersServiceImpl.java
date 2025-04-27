package my.pet_projects.booking_system.services;

import lombok.RequiredArgsConstructor;
import my.pet_projects.booking_system.models.User;
import my.pet_projects.booking_system.repositories.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    @Override
    public Optional<User> findUserByEmail (String email) {
        return usersRepository.findUserByEmail(email);
    }

    @Override
    public Boolean userExists(String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        usersRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return usersRepository.findById(userId);
    }
}
