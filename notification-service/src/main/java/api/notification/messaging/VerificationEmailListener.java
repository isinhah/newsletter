package api.notification.messaging;

import api.notification.dto.VerificationEmailDto;
import api.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerificationEmailListener {

    private final EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue.verification.name}")
    public void receiveVerificationEmail(VerificationEmailDto dto) {
        emailService.sendVerificationEmail(dto);
    }
}