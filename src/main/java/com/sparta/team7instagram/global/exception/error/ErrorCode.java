package com.sparta.team7instagram.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다"),

    // feed
    FEED_NOT_FOUND(HttpStatus.NOT_FOUND, "피드가 존재하지 않습니다"),

    // comment



    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),

    // Auth
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND,"이메일이 존재하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"wrong password"),
    EXISTING_EMAIL(HttpStatus.BAD_REQUEST,"이미 존재하는 이메일입니다."),
    DIFFERENT_USER(HttpStatus.UNAUTHORIZED,"다른 유저입니다.");

    private final HttpStatus status;
    private final String message;
}
