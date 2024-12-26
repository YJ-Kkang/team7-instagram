package com.sparta.team7instagram.domain.auth.exception;

import com.sparta.team7instagram.global.exception.CustomRuntimeException;
import com.sparta.team7instagram.global.exception.error.ErrorCode;

public class DifferentUserException extends CustomRuntimeException {
    public DifferentUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}