package com.sparta.team7instagram.domain.user.exception;

import com.sparta.team7instagram.global.exception.CustomRuntimeException;
import com.sparta.team7instagram.global.exception.error.ErrorCode;

public class InvaildFollowException extends CustomRuntimeException {
    public InvaildFollowException(ErrorCode errorCode) {
        super(errorCode);
    }
}