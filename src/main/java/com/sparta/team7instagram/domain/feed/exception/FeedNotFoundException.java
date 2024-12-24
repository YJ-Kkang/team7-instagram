package com.sparta.team7instagram.domain.feed.exception;

import com.sparta.team7instagram.global.exception.CustomRuntimeException;
import com.sparta.team7instagram.global.exception.error.ErrorCode;

public class FeedNotFoundException extends CustomRuntimeException {
    public FeedNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
