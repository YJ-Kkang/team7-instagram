package com.sparta.team7instagram.domain.feed.exception;

import com.sparta.team7instagram.global.exception.CustomRuntimeException;
import com.sparta.team7instagram.global.exception.error.ErrorCode;

public class CannotLikeMyFeedException extends CustomRuntimeException {
    public CannotLikeMyFeedException(ErrorCode errorCode) {
        super(errorCode);
    }
}

