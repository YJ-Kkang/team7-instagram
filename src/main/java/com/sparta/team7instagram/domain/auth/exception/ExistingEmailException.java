package com.sparta.team7instagram.domain.auth.exception;

import com.sparta.team7instagram.global.exception.CustomRuntimeException;
import com.sparta.team7instagram.global.exception.error.ErrorCode;

public class ExistingEmailException extends CustomRuntimeException {
    public ExistingEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}