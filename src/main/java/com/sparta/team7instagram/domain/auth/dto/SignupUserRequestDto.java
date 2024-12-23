package com.sparta.team7instagram.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupUserRequestDto {
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 대소문자 영문, 숫자, 특수문자를 포함하여 최소 8자 이상이어야 합니다."
    )
    private String password;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Pattern(
            regexp = "^[가-힣]{1,4}$",
            message = "이름은 1~4자 이하의 한글만 가능합니다."
    )
    private String name;
}
