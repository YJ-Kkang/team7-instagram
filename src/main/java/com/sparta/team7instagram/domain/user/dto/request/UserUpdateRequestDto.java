package com.sparta.team7instagram.domain.user.dto.request;

import com.sparta.team7instagram.domain.user.dto.UserValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class UserUpdateRequestDto {
    @Pattern(
            regexp = UserValidationMessages.NAME_REGEXP,
            message = UserValidationMessages.NAME_INVALID)
    @NotBlank
    private String name;

    @Size(max = 30, message = UserValidationMessages.INTRO_INVALID)
    private String intro;
}
