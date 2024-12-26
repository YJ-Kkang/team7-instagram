package com.sparta.team7instagram.domain.user.dto.request;

import com.sparta.team7instagram.domain.user.dto.UserValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class UserPasswordUpdateRequestDto {
    @Pattern(
            regexp = UserValidationMessages.PASWORD_REGEXP,
            message = UserValidationMessages.PASSWORD_INVALID
    )
    @NotBlank
    private String currentPassword;

    @Pattern(
            regexp = UserValidationMessages.PASWORD_REGEXP,
            message = UserValidationMessages.PASSWORD_INVALID
    )
    @NotBlank
    private String changedPassword;
}
