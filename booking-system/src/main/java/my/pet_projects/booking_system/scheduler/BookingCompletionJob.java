package my.pet_projects.booking_system.scheduler;

import lombok.RequiredArgsConstructor;
import my.pet_projects.booking_system.models.Booking;
import my.pet_projects.booking_system.models.BookingStatus;
import my.pet_projects.booking_system.services.BookingService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class BookingCompletionJob extends QuartzJobBean {
    private final BookingService bookingService;

    @Override
    @Transactional
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LocalDateTime now = LocalDateTime.now();

        List<Booking> bookings = bookingService.getBookingsToBeCompleted(now, BookingStatus.ACTIVE);

        bookings.forEach(booking -> {
            booking.setStatus(BookingStatus.COMPLETED);
            bookingService.saveBooking(booking);
        });

    }
}
