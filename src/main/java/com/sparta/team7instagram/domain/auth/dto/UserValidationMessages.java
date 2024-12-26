package com.sparta.team7instagram.domain.auth.dto;

public class UserValidationMessages {

    // 이메일 관련 메시지
    public static final String EMAIL_REQUIRED = "이메일은 필수 입력값입니다.";
    public static final String EMAIL_INVALID = "유효하지 않은 이메일 형식입니다.";

    // 비밀번호 관련 메시지
    public static final String PASSWORD_REQUIRED = "비밀번호는 필수 입력값입니다.";
    public static final String PASSWORD_INVALID = "비밀번호는 대소문자 영문, 숫자, 특수문자를 포함하여 최소 8자 이상이어야 합니다.";

    // 이름 관련 메시지
    public static final String NAME_REQUIRED = "이름은 필수 입력값입니다.";
    public static final String NAME_INVALID = "이름은 1~4자 이하의 한글만 가능합니다.";
}
