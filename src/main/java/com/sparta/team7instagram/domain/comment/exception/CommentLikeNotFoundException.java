package com.sparta.team7instagram.domain.comment.exception;

import com.sparta.team7instagram.global.exception.CustomRuntimeException;
import com.sparta.team7instagram.global.exception.error.ErrorCode;

public class CommentLikeNotFoundException extends CustomRuntimeException {
    public CommentLikeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
