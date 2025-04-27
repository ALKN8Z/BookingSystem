package my.pet_projects.booking_system.services;

import my.pet_projects.booking_system.dto.requests.BookingRequest;
import my.pet_projects.booking_system.dto.responses.BookingResponse;
import my.pet_projects.booking_system.models.Booking;
import my.pet_projects.booking_system.models.BookingStatus;
import my.pet_projects.booking_system.models.User;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);
    void cancelBooking(Long bookingId) throws AccessDeniedException;
    List<Booking> getUserBookings(List<BookingStatus> statuses);
    List<Booking> getBookingsToBeNotified(LocalDateTime start, LocalDateTime end);
    void saveBooking(Booking booking);
    List<Booking> getBookingsToBeCompleted(LocalDateTime currentTime, BookingStatus status);
    BookingResponse getBooking(Long bookingId);

}
