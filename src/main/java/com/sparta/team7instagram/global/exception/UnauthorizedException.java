package com.sparta.team7instagram.global.exception;

import com.sparta.team7instagram.global.exception.error.ErrorCode;

public class UnauthorizedException extends CustomRuntimeException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}