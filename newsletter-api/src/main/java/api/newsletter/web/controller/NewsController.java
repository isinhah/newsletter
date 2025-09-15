package api.newsletter.web.controller;

import api.newsletter.model.NewsArticle;
import api.newsletter.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @PostMapping("/fetch-and-send")
    public ResponseEntity<String> fetchAndSendNewsManually() {
        List<NewsArticle> articles = newsService.fetchAndSaveArticles();
        newsService.fetchAndSendNewsManually(articles);
        return ResponseEntity.ok("News fetched and sent successfully.");
    }
}