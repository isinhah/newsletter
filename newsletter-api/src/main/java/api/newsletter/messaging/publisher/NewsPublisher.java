package api.newsletter.messaging.publisher;

import api.newsletter.messaging.dto.NewsNotificationDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String routingKey;

    public NewsPublisher(RabbitTemplate rabbitTemplate,
                         @Value("${spring.rabbitmq.queue.notification.name}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.routingKey = routingKey;
    }

    public void publishNews(List<NewsNotificationDto> newsList) {
        rabbitTemplate.convertAndSend(routingKey, newsList);
    }
}