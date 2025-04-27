package my.pet_projects.booking_system.repositories;

import my.pet_projects.booking_system.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Room, Long> {
    Room findByName(String name);

    @Query("select r from Room r where r.capacity >= :requiredCapacity and not exists" +
            " (select b from Booking b where b.room = r and b.status = 'ACTIVE' and " +
            "(b.startTime < :endTime and b.endTime > :startTime))")
    List<Room> findAvailableRooms(
            @Param("requiredCapacity") int requiredCapacity,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
