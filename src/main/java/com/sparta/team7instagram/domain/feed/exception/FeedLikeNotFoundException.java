package com.sparta.team7instagram.domain.feed.exception;

import com.sparta.team7instagram.global.exception.CustomRuntimeException;
import com.sparta.team7instagram.global.exception.error.ErrorCode;

public class FeedLikeNotFoundException extends CustomRuntimeException {
    public FeedLikeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
