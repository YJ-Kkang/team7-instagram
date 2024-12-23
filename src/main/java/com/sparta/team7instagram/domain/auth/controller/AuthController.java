package com.sparta.team7instagram.domain.auth.controller;

import com.sparta.team7instagram.domain.auth.dto.SignupUserRequestDto;
import com.sparta.team7instagram.domain.auth.service.UserService;
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
}