package my.pet_projects.booking_system.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import my.pet_projects.booking_system.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Boolean existsByEmail(String email);
}
