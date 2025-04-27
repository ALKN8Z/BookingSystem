package my.pet_projects.booking_system.scheduler;

import lombok.RequiredArgsConstructor;
import my.pet_projects.booking_system.models.Booking;
import my.pet_projects.booking_system.repositories.BookingsRepository;
import my.pet_projects.booking_system.services.BookingService;
import my.pet_projects.booking_system.services.NotificationService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class BookingNotificationJob extends QuartzJobBean {
    private final BookingService bookingService;
    private final NotificationService notificationService;

    @Override
    @Transactional
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderTime = now.plusHours(1);

        List<Booking> bookings = bookingService.getBookingsToBeNotified(now, reminderTime);

        bookings.forEach(booking -> {
            notificationService.sendBookingReminderEmail(booking);
            booking.setNotified(true);
            bookingService.saveBooking(booking);
        });
    }
}
