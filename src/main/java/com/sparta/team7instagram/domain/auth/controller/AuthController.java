package com.sparta.team7instagram.domain.auth.controller;

import com.sparta.team7instagram.domain.auth.dto.requestDto.LoginUserRequestDto;
import com.sparta.team7instagram.domain.auth.dto.requestDto.SignupUserRequestDto;
import com.sparta.team7instagram.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.sparta.team7instagram.global.util.SessionUtil.removeSession;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * signup
     *
     * @param requestDto SignupUserRequestDto
     * @return 201 Created(uri)
     */
    @PostMapping("/signup")
    public ResponseEntity<Void> saveUser(
            @Valid @RequestBody final SignupUserRequestDto requestDto
    ) {
        final Long userId = authService.saveUser(requestDto);

        final URI uri = UriComponentsBuilder.fromPath("/users/{userId}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    /**
     * login
     *
     * @param requestDto LoginUserRequestDto
     * @param request    HttpServletRequest
     * @return 200 Ok
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody final LoginUserRequestDto requestDto,
            HttpServletRequest request
    ) {
        authService.login(requestDto, request);

        return ResponseEntity.ok().build();
    }

    /**
     * logout
     *
     * @param session HttpSession
     * @return 204 NoContent
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpSession session
    ) {
        removeSession(session);
        return ResponseEntity.noContent().build();
    }
}