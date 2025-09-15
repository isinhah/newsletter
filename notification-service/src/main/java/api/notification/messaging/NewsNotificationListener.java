package api.notification.messaging;

import api.notification.dto.NewsNotificationDto;
import api.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsNotificationListener {

    private final EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue.notification.name}")
    public void receiveNews(List<NewsNotificationDto> newsList) {
        String destinatario = "contato.isabelhenrique@gmail.com";
        emailService.sendNewsEmail(destinatario, newsList);
    }
}