package api.newsletter.messaging.dto;

public record VerificationEmailDto(
        String email,
        String verificationToken
) {
}