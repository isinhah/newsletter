package api.notification.dto;

public record VerificationEmailDto(
        String email,
        String verificationToken
) {}