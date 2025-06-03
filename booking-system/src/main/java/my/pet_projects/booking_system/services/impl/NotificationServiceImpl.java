package my.pet_projects.booking_system.services.impl;

import lombok.RequiredArgsConstructor;
import my.pet_projects.booking_system.models.Booking;
import my.pet_projects.booking_system.models.User;
import my.pet_projects.booking_system.services.EmailService;
import my.pet_projects.booking_system.services.NotificationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final EmailService emailService;

    @Override
    public void sendBookingReminderEmail(Booking booking) {
        Map<String, Object> model = new HashMap<>();
        model.put("roomName", booking.getRoom().getName());
        model.put("startTime", booking.getStartTime());

        emailService.sendHtmlEmail(booking.getUser().getEmail(),
                "Напоминание о бронировании",
                "booking-reminder", model);
    }

    @Override
    public void sendWelcomeNotification(User user) {
        Map<String, Object> model = new HashMap<>();
        model.put("username", user.getEmail().split("@")[0]);
        model.put("email", user.getEmail());

        emailService.sendHtmlEmail(user.getEmail(),
                "Добро пожаловать!",
                "welcome-email",
                model);
    }
}
