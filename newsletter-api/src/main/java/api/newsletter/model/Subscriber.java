package api.newsletter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "subscribers")
public class Subscriber implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriberStatus status = SubscriberStatus.UNSUBSCRIBED;

    @Column(nullable = false, name = "is_verified")
    private boolean verified = false;

    @CreationTimestamp
    @Column(nullable = false, name = "subscription_date")
    private Instant subscriptionDate;

    @Column(unique = true, name = "verification_token", length = 36)
    private String verificationToken;

    @Column(unique = true, length = 36)
    private String unsubscribeToken;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}