package api.newsletter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "news_articles")
public class NewsArticle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true, length = 500)
    private String description;

    @Column(nullable = false, length = 2048)
    private String url;

    @Column(nullable = true, length = 2048)
    private String urlToImage;

    @Column(nullable = false, name = "publication_date")
    private Instant publicationDate;

    @Lob
    private String content;

    @Column(nullable = false, length = 100, name = "source_name")
    private String sourceName;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NewsArticle that = (NewsArticle) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
