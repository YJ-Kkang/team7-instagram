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

/**
 * 유저 관련 데이터를 처리하는 유저 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    // 속성
    private final UserService userService;

    // 기능

    /**
     * 유저 프로필 조회 API
     * JSON
     * 요청------------------
     * GET /users/1
     * 응답------------------
     * {
     * "name": "이름",
     * "intro": "소개",
     * "followNum": 1,
     * "followerNum": 1,
     * "feedNum": 0,
     * "feeds": {}
     * }
     */
    @GetMapping("/{usedId}")
    public ResponseEntity<UserResponseDto> getUserProfile(
            @PathVariable Long usedId
    ) {
        return ResponseEntity.ok(userService.getUserProfile(usedId));
    }

    /**
     * 유저 이름 검색 API
     * 요청-------------
     * GET /users/search?name=
     * 응답-------------
     * [
     *   {
     *        "name": "조민재",
     *        "intro": null,
     *        "followingNum": 0,
     *        "followerNum": 0,
     *        "feedNum": 0,
     *        "feeds": null
     *   }
     *   {
     *         "name": "조민재",
     *         "intro": null,
     *         "followingNum": 0,
     *         "followerNum": 0,
     *         "feedNum": 0,
     *         "feeds": null
     *   }
     * ]
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> searchUsersByName(@RequestParam String name) {
        List<UserResponseDto> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }

    /**
     * 유저 수정 API
     * 요청-------------
     * PATCH /users/1
     */
    @PatchMapping("/{usedId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long usedId,
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto
    ) {
        userService.updateUser(usedId, userUpdateRequestDto);
        return ResponseEntity.ok(userService.getUserProfile(usedId));
    }

    /**
     * 유저 비밀번호 수정 API
     * 요청--------------
     * PATCH /users/1/password
     */
    @PatchMapping("/{usedId}/password")
    public ResponseEntity<UserResponseDto> updatePassword(
            @PathVariable Long usedId,
            @Valid @RequestBody UserPasswordUpdateRequestDto userPasswordUpdateRequestDto
    ) {
        return ResponseEntity.ok(userService.updatePassword(usedId, userPasswordUpdateRequestDto));
    }

    /**
     * 유저 삭제 API
     * 요청-------------
     * DELETE /users/1
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            HttpSession session,
            @RequestParam String password // 요청에 비밀번호 추가
    ) {
        String userId = String.valueOf(session.getAttribute("userId"));
        userService.deleteUser(Long.valueOf(userId), password, session);
        return ResponseEntity.ok().build();
    }

    /**
     * 팔로우 생성 API
     * 호출 주체(followerId)의 following 목록에 유저 추가
     * -> 대상 유저(followingId)의 followerNum 증가
     * 요청-------------
     * POST /users/follows/{followingId}
     */
    @PostMapping("/follows/{followingId}")
    public ResponseEntity<Void> followUser(
            @PathVariable Long followingId,
                          HttpSession session
    ) {
        String followId = String.valueOf(session.getAttribute("userId"));
        userService.followUser(Long.valueOf(followId),followingId);
        return ResponseEntity.ok().build();
    }

    /**
     * 팔로우 취소 API
     * 호출 주체(followerId)의 following 목록에서 유저 제거
     * -> 대상 유저(followingId)의 followerNum 감소
     * DELETE /users/follows/{followingId}
     */
    @DeleteMapping("/follows/{followingId}")
    public ResponseEntity<Void> unfollowUser(
            @PathVariable Long followingId,
                          HttpSession session
    ) {
        String followId = String.valueOf(session.getAttribute("userId"));
        userService.unfollowUser(Long.valueOf(followId),followingId);
        return ResponseEntity.ok().build();
    }

}
