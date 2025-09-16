package api.notification.service;

import api.notification.dto.NewsNotificationDto;
import api.notification.dto.VerificationEmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

import static java.awt.SystemColor.text;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendNewsEmail(String to, List<NewsNotificationDto> newsList) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("📰 Suas notícias diárias");

            Context context = new Context();
            context.setVariable("newsList", newsList);

            String body = templateEngine.process("email/news", context);
            helper.setText(body, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Error sending news email", e);
        }
    }

    public void sendVerificationEmail(VerificationEmailDto dto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(dto.email());
            helper.setSubject("✅ Confirme seu cadastro");

            Context context = new Context();
            String verificationLink = "http://localhost:8080/api/subscribers/verify?token=" + dto.verificationToken();
            context.setVariable("verificationLink", verificationLink);

            String body = templateEngine.process("email/verification", context);
            helper.setText(body, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Error sending verification email", e);
        }
    }
}