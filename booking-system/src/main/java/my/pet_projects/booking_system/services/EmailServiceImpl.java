package my.pet_projects.booking_system.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;


    @Override
    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> model) {
        Context context = new Context();
        context.setVariables(model);

        String html = templateEngine.process(templateName, context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Ошибка отправки письма: " + e);
        }
    }
}
