package api.newsletter.repository;

import api.newsletter.model.Subscriber;
import api.newsletter.model.SubscriberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    Subscriber findByEmail(String email);
    Optional<Subscriber> findByVerificationToken(String token);
    Optional<Subscriber> findByUnsubscribeToken(String token);
    Page<Subscriber> findByStatus(SubscriberStatus status, Pageable pageable);
    List<Subscriber> findByVerifiedTrueAndStatus(SubscriberStatus status);
}