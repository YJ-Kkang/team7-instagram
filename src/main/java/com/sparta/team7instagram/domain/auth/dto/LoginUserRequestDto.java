package com.sparta.team7instagram.domain.auth.dto;

import com.sparta.team7instagram.domain.auth.common.UserValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginUserRequestDto (
    @NotBlank(message = UserValidationMessages.EMAIL_REQUIRED)
    @Email(message = UserValidationMessages.EMAIL_INVALID)
    String email,

    @NotBlank(message = UserValidationMessages.PASSWORD_REQUIRED)
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = UserValidationMessages.PASSWORD_INVALID
    )
    String password
    ){
}
