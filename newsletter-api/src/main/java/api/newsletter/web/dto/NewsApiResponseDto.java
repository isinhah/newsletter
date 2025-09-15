package api.newsletter.web.dto;

import java.time.Instant;
import java.util.List;

public record NewsApiResponseDto(
        int totalArticles,
        List<ArticleDto> articles
) {

    public record ArticleDto(
            String id,
            String title,
            String description,
            String content,
            String url,
            String image,
            Instant publishedAt,
            SourceDto source
    ) {}

    public record SourceDto(
            String id,
            String name,
            String url
    ) {}
}