package com.sparta.team7instagram.global.util;

import com.sparta.team7instagram.global.exception.UnauthorizedException;
import com.sparta.team7instagram.global.exception.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtil {

    public static final String SESSION_KEY = "USER-ID";

    public static void createSession(Long id, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();

        session.setAttribute(SESSION_KEY, id);
    }

    public static Long getSession(HttpSession session) {
        return (Long) session.getAttribute(SESSION_KEY);
    }

    public static void removeSession(HttpSession session) {
        session.invalidate();
    }

    public static void validateSession(HttpSession session) {
        if (session == null || getSession(session) == null) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        }
    }
}
