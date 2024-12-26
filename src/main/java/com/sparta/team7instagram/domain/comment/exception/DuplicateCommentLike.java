package com.sparta.team7instagram.domain.comment.exception;

import com.sparta.team7instagram.global.exception.CustomRuntimeException;
import com.sparta.team7instagram.global.exception.error.ErrorCode;

public class DuplicateCommentLike extends CustomRuntimeException {
    public DuplicateCommentLike(ErrorCode errorCode) {
        super(errorCode);
    }
}
