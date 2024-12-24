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
    FEED_NOT_FOUND(HttpStatus.NOT_FOUND, "피드가 존재하지 않습니다");

    // comment


    private final HttpStatus status;
    private final String message;
}
