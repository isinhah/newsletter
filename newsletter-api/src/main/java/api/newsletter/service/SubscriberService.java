package api.newsletter.service;

import api.newsletter.messaging.publisher.EmailPublisher;
import api.newsletter.model.Subscriber;
import api.newsletter.model.SubscriberStatus;
import api.newsletter.repository.SubscriberRepository;
import api.newsletter.web.dto.SubscriberRegisterDto;
import api.newsletter.web.dto.SubscriberResponseDto;
import api.newsletter.mapper.SubscriberMapper;
import api.newsletter.web.exception.InvalidSubscriberStatusException;
import api.newsletter.web.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final EmailPublisher emailPublisher;

    public Page<SubscriberResponseDto> getSubscribersByStatus(String status, Pageable pageable) {
        if (status == null || status.isBlank()) {
            Page<Subscriber> allSubscribers = subscriberRepository.findAll(pageable);
            return allSubscribers.map(SubscriberMapper.INSTANCE::toDto);
        }

        SubscriberStatus subscriberStatus;
        try {
            subscriberStatus = SubscriberStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidSubscriberStatusException("Invalid status: " + status);
        }

        Page<Subscriber> subscribers = subscriberRepository.findByStatus(subscriberStatus, pageable);
        return subscribers.map(SubscriberMapper.INSTANCE::toDto);
    }

    public SubscriberResponseDto registerSubscriber(SubscriberRegisterDto registerDto) {
        Subscriber subscriber = subscriberRepository.findByEmail(registerDto.email());

        if (subscriber == null) {
            subscriber = SubscriberMapper.INSTANCE.toEntity(registerDto);
            return initializeSubscriber(subscriber);
        } else {
            if (subscriber.getStatus() == SubscriberStatus.UNSUBSCRIBED || subscriber.getStatus() == SubscriberStatus.PENDING) {
                return initializeSubscriber(subscriber);
            } else {
                return SubscriberMapper.INSTANCE.toDto(subscriber);
            }
        }
    }

    public void verifyToken(String token) {
        Subscriber subscriber = subscriberRepository.findByVerificationToken(token)
                .orElseThrow(() -> new InvalidTokenException("Invalid token or it has already been used."));

        subscriber.setVerified(true);
        subscriber.setStatus(SubscriberStatus.ACTIVE);
        subscriber.setVerificationToken(null);

        subscriberRepository.save(subscriber);
    }

    public void unsubscribe(String token) {
        Subscriber subscriber = subscriberRepository.findByUnsubscribeToken(token)
                .orElseThrow(() -> new InvalidTokenException("Invalid token."));

        subscriber.setVerified(false);
        subscriber.setStatus(SubscriberStatus.UNSUBSCRIBED);
        subscriber.setVerificationToken(null);
        subscriber.setUnsubscribeToken(null);

        subscriberRepository.save(subscriber);
    }

    private SubscriberResponseDto initializeSubscriber(Subscriber subscriber) {
        subscriber.setStatus(SubscriberStatus.PENDING);
        subscriber.setVerified(false);
        subscriber.setVerificationToken(UUID.randomUUID().toString());
        subscriber.setUnsubscribeToken(UUID.randomUUID().toString());

        Subscriber savedSubscriber = subscriberRepository.save(subscriber);
        emailPublisher.publishVerificationEmail(savedSubscriber);
        return SubscriberMapper.INSTANCE.toDto(savedSubscriber);
    }
}