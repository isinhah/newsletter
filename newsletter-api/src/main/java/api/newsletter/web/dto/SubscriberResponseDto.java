package api.newsletter.web.dto;

import api.newsletter.model.SubscriberStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public record SubscriberResponseDto(
        Long id,
        String name,
        String email,
        boolean verified,
        SubscriberStatus status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        Instant subscriptionDate
) {
}