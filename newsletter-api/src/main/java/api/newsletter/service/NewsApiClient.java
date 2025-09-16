package api.newsletter.service;

import api.newsletter.web.dto.NewsApiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class NewsApiClient {

    @Value("${news.api.key}")
    private String apiKey;

    @Value("${news.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("news_cache")
    public NewsApiResponseDto fetchLatestNews() {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("lang", "pt")
                .queryParam("country", "br")
                .queryParam("max", 10)
                .queryParam("apikey", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, NewsApiResponseDto.class);
    }
}