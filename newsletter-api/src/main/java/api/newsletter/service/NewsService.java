package api.newsletter.service;

import api.newsletter.messaging.dto.NewsNotificationDto;
import api.newsletter.messaging.publisher.NewsPublisher;
import api.newsletter.model.NewsArticle;
import api.newsletter.model.SubscriberStatus;
import api.newsletter.repository.NewsArticleRepository;
import api.newsletter.repository.SubscriberRepository;
import api.newsletter.web.dto.NewsApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NewsArticleRepository newsArticleRepository;
    private final SubscriberRepository subscriberRepository;
    private final NewsApiClient newsApiClient;
    private final NewsPublisher newsPublisher;

    public List<NewsArticle> fetchAndSaveArticles() {
        NewsApiResponseDto response = newsApiClient.fetchLatestNews();

        List<NewsArticle> articles = response.articles().stream()
                .map(dto -> {
                    NewsArticle article = new NewsArticle();
                    article.setTitle(dto.title());
                    article.setDescription(dto.description());
                    article.setUrl(dto.url());
                    article.setUrlToImage(dto.image());
                    article.setPublicationDate(dto.publishedAt());
                    article.setContent(dto.content());
                    article.setSourceName(dto.source().name());
                    return article;
                })
                .toList();

        return newsArticleRepository.saveAll(articles);
    }

    @Scheduled(cron = "0 0 11 * * *")
    public void sendDailyNews() {
        List<NewsArticle> articles = fetchAndSaveArticles();
        sendNews(articles);
    }

    public void fetchAndSendNewsManually(List<NewsArticle> articles) {
        sendNews(articles);
    }

    private void sendNews(List<NewsArticle> articles) {
        List<NewsNotificationDto> dtos = articles.stream()
                .map(article -> new NewsNotificationDto(
                        article.getTitle(),
                        article.getDescription(),
                        article.getUrl(),
                        article.getUrlToImage(),
                        article.getPublicationDate(),
                        article.getContent(),
                        article.getSourceName()
                ))
                .toList();

        var verifiedSubscribers = subscriberRepository.findByVerifiedTrueAndStatus(SubscriberStatus.ACTIVE);

        if (verifiedSubscribers.isEmpty()) {
            log.info("No active and verified subscribers found. News not sent.");
            return;
        }

        newsPublisher.publishNews(dtos);
    }
}