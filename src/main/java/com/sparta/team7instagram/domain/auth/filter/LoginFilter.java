package com.sparta.team7instagram.domain.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.team7instagram.global.exception.UnauthorizedException;
import com.sparta.team7instagram.global.exception.error.ErrorCode;
import com.sparta.team7instagram.global.exception.error.ErrorResponse;
import com.sparta.team7instagram.global.util.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {
    // 인증을 하지 않아도될 URL Path 배열
    private static final String[] WHITE_LIST = {"/auth/signup", "/auth/login"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        // 다양한 기능을 사용하기 위해 다운 캐스팅
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        log.info("로그인 필터 로직 실행");

        // 로그인을 체크 해야하는 URL인지 검사
        // whiteListURL에 포함된 경우 true 반환 -> !true = false
        try {
            if (!isWhiteList(requestURI) && !HttpMethod.GET.matches(httpRequest.getMethod())) {
                SessionUtil.validateSession(httpRequest.getSession(false));
            }
        } catch (UnauthorizedException e) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");

            ErrorResponse errorResponse = new ErrorResponse(ErrorCode.UNAUTHORIZED.getStatus(), ErrorCode.UNAUTHORIZED.getMessage());

            ObjectMapper objectMapper = new ObjectMapper();
            httpResponse.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        }

        chain.doFilter(request, response);
    }

    // 로그인 여부를 확인하는 URL인지 체크하는 메서드
    private boolean isWhiteList(
            String requestURI
    ) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}