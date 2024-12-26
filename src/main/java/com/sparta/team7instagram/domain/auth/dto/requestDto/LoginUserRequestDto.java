package com.sparta.team7instagram.domain.auth.dto.requestDto;

import com.sparta.team7instagram.domain.user.dto.UserValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginUserRequestDto(
        @NotBlank(message = UserValidationMessages.EMAIL_REQUIRED)
        @Pattern(
                regexp = UserValidationMessages.EMAIL_REGEXP,
                message = UserValidationMessages.EMAIL_INVALID
        )
        String email,

        @NotBlank(message = UserValidationMessages.PASSWORD_REQUIRED)
        @Pattern(
                regexp = UserValidationMessages.PASSWORD_REGEXP,
                message = UserValidationMessages.PASSWORD_INVALID
        )
        String password
) {
}