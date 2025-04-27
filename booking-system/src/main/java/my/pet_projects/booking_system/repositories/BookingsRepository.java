package my.pet_projects.booking_system.repositories;

import my.pet_projects.booking_system.models.Booking;
import my.pet_projects.booking_system.models.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT count(b) from Booking b where b.room.id = :roomId and b.status = 'ACTIVE' and b.startTime < :endTime and b.endTime > :startTime")
    int countOverLappingBookings(
            @Param("roomId") Long roomId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("select b from Booking b where b.user.id = :userId and b.status in :statuses")
    List<Booking> findByUserIdAndStatus(
            @Param("userId") Long userId,
            @Param("statuses") List<BookingStatus> statuses
    );

    @Query("SELECT b from Booking b left join b.room left join b.user where b.startTime between :start and :end and b.notified=false ")
    List<Booking> findByStartTimeBetweenAndNotifiedFalse(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("select b from Booking b where b.endTime < :currentTime and b.status = :status")
    List<Booking> findByEndTimeBeforeAndStatus(
            @Param("currentTime") LocalDateTime currentTime,
            @Param("status")BookingStatus status
    );

}
