package com.sparta.team7instagram.domain.user.controller;

import com.sparta.team7instagram.domain.user.dto.request.UserPasswordUpdateRequestDto;
import com.sparta.team7instagram.domain.user.dto.request.UserUpdateRequestDto;
import com.sparta.team7instagram.domain.user.dto.response.UserResponseDto;
import com.sparta.team7instagram.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * 유저 프로필 조회 API
     * JSON
     * 요청------------------
     * GET /users/1
     * 응답------------------
     * {
     *      "name": "이름",
     *      "intro": "소개",
     *      "followingNum": 1,
     *      "followerNum": 1,
     *      "feedNum": 0,
     *      "feeds": {}
     * }
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserProfile(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    /**
     * 유저 이름 검색 API
     * @param name:유저명
     * @return [ "유저 리스트" ]
     */
    @GetMapping("/search")
    public ResponseEntity<List<String>> searchUsersByName(@RequestParam String name) {
        List<String> userNames = userService.searchUsersByName(name);
        return ResponseEntity.ok(userNames);
    }

    /**
     * 유저 수정 API
     * 요청----------
     * PATCH /users/1
     *  {
     *      "name": "바뀐이름",
     *      "intro": "수정된 소개글"
     *  }
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto
    ) {
        userService.updateUser(userId, userUpdateRequestDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * 유저 비밀번호 수정 API
     * 요청--------------
     * PATCH /users/1/password
     * {
     *      "currentPassword": "현재 비밀번호"
     *      "changedPassword": "수정된 비밀번호"
     * }
     */
    @PatchMapping("/{userId}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long userId,
            @Valid @RequestBody UserPasswordUpdateRequestDto userPasswordUpdateRequestDto
    ) {
        userService.updatePassword(userId, userPasswordUpdateRequestDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * 유저 삭제 API
     * @param password:현재 비밀번호
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            HttpSession session,
            @RequestParam String password // 요청에 비밀번호 추가
    ) {
        String userId = String.valueOf(session.getAttribute("userId"));
        userService.deleteUser(Long.valueOf(userId), password, session);
        return ResponseEntity.noContent().build();
    }

    /**
     * 팔로우 생성 API
     */
    @PostMapping("/follows/{followingId}")
    public ResponseEntity<Void> followUser(
            @PathVariable Long followingId,
                          HttpSession session
    ) {
        String followId = String.valueOf(session.getAttribute("userId"));
        userService.followUser(Long.valueOf(followId),followingId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 팔로우 취소 API
     */
    @DeleteMapping("/follows/{followingId}")
    public ResponseEntity<Void> unfollowUser(
            @PathVariable Long followingId,
                          HttpSession session
    ) {
        String followId = String.valueOf(session.getAttribute("userId"));
        userService.unfollowUser(Long.valueOf(followId),followingId);
        return ResponseEntity.noContent().build();
    }

}
