package api.newsletter.messaging.publisher;

import api.newsletter.messaging.dto.VerificationEmailDto;
import api.newsletter.model.Subscriber;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String routingKey;

    public EmailPublisher(RabbitTemplate rabbitTemplate,
                          @Value("${spring.rabbitmq.queue.verification.name}") String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.routingKey = routingKey;
    }

    public void publishVerificationEmail(Subscriber savedSubscriber) {
        VerificationEmailDto event = new VerificationEmailDto(
                savedSubscriber.getEmail(),
                savedSubscriber.getVerificationToken()
        );

        rabbitTemplate.convertAndSend(routingKey, event);
    }
}