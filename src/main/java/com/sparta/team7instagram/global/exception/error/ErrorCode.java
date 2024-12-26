package com.sparta.team7instagram.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다"),
    SAME_PASSWORD(HttpStatus.NOT_FOUND, "현재 비밀번호와 동일한 비밀번호로 수정할 수 없습니다."),

    // feed
    FEED_NOT_FOUND(HttpStatus.NOT_FOUND, "피드가 존재하지 않습니다"),
    FEED_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "피드에 유저의 좋아요가 존재하지 않습니다"),

    // comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다"),

    COMMENT_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요가 존재하지 않습니다"),

    DUPLICATE_COMMENT_LIKE(HttpStatus.CONFLICT, "좋아요를 중복으로 누를 수 없습니다"),
    SELF_COMMENT_LIKE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "본인이 작성한 댓글에는 좋아요를 누를 수 없습니다"),

    // follow
    NOT_SELF_FOLLOW(HttpStatus.BAD_REQUEST, "자기 자신을 팔로우할 수 없습니다."),
    EXISTING_FOLLOW(HttpStatus.BAD_REQUEST, "이미 팔로우 상태입니다."),
    NOT_FOLLOWING(HttpStatus.BAD_REQUEST, "이미 팔로우 상태가 아닙니다."),


    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),

    // Auth
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "이메일이 존재하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "wrong password"),
    EXISTING_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    DEACTIVATED_EMAIL(HttpStatus.UNAUTHORIZED, "탈퇴한 회원의 이메일입니다."),
    DIFFERENT_USER(HttpStatus.UNAUTHORIZED, "다른 유저입니다.");

    private final HttpStatus status;
    private final String message;
}
