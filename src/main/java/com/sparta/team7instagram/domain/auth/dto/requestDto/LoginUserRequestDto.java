package com.sparta.team7instagram.domain.auth.dto.requestDto;

import com.sparta.team7instagram.domain.auth.dto.UserValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginUserRequestDto(
        @NotBlank(message = UserValidationMessages.EMAIL_REQUIRED)
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]$",
                message = UserValidationMessages.EMAIL_INVALID
        )
        String email,

        @NotBlank(message = UserValidationMessages.PASSWORD_REQUIRED)
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = UserValidationMessages.PASSWORD_INVALID
        )
        String password
) {
}
