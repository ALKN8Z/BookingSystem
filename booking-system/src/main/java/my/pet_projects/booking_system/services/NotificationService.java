package my.pet_projects.booking_system.services;

import my.pet_projects.booking_system.models.Booking;
import my.pet_projects.booking_system.models.User;

import java.util.Map;

public interface NotificationService {
    void sendBookingReminderEmail(Booking booking);
    void sendWelcomeNotification(User user);
}
