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
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다"),

    COMMENT_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요가 존재하지 않습니다"),

    DUPLICATE_COMMENT_LIKE(HttpStatus.CONFLICT, "좋아요를 중복으로 누를 수 없습니다"),
    SELF_COMMENT_LIKE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "본인이 작성한 댓글에는 좋아요를 누를 수 없습니다"),




    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
