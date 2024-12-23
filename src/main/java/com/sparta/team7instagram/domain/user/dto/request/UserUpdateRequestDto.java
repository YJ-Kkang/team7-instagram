package com.sparta.team7instagram.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 유저 정보를 수정하는 요청 DTO (유저명, 소개글)
 */
@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    @Pattern(
            regexp = "^[가-힣]{1,4}$",
            message = "유저명은 4글자 이하의 한글") // 유저명은 한글만 포함, 4글자 이하
    @NotBlank
    private String name; // 유저명

    @Size(max = 30, message = "소개글은 최대 30자까지 입력 가능합니다.") // null 가능, 30글자 이하
    private String intro; // 소개글
}
