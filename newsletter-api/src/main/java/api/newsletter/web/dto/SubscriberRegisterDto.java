package api.newsletter.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SubscriberRegisterDto(
        @NotBlank(message = "The name cannot be blank")
        String name,
        @NotBlank(message = "The email cannot be blank")
        @Email(message = "The email must have a valid format")
        String email
) {
}