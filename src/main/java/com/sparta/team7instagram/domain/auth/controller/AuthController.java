package com.sparta.team7instagram.domain.auth.controller;

import com.sparta.team7instagram.domain.auth.dto.LoginUserRequestDto;
import com.sparta.team7instagram.domain.auth.dto.SignupUserRequestDto;
import com.sparta.team7instagram.domain.auth.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * signup
     * @param requestDto
     * @return 생성한 user
     */
    @PostMapping("/signup")
    public ResponseEntity<Void> saveUser(
            @Valid @RequestBody SignupUserRequestDto requestDto){
        userService.saveUser(requestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * login
     * @param requestDto
     * @param request
     * @return 성공 여부
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginUserRequestDto requestDto,
            HttpServletRequest request){
        userService.login(requestDto, request);

        return ResponseEntity.ok("success");
    }
}