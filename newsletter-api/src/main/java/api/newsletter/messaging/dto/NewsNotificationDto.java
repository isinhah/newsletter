package api.newsletter.messaging.dto;

import java.time.Instant;

public record NewsNotificationDto(
        String title,
        String description,
        String url,
        String urlToImage,
        Instant publicationDate,
        String content,
        String sourceName
) {}
