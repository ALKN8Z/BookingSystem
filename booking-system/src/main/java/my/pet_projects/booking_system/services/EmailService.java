package my.pet_projects.booking_system.services;

import java.util.Map;

public interface EmailService {
    void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> model);
}
