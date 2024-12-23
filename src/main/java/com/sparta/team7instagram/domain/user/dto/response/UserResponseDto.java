package com.sparta.team7instagram.domain.user.dto.response;

import com.sparta.team7instagram.domain.user.entity.UserEntity;
import lombok.*;

import java.util.List;

/**
 * User 엔티티를 기반으로 클라이언트에게 유저 정보 반환
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private String name; // 유저명
    private String intro; // 소개글
    private int followingNum; // 팔로잉 수
    private int followerNum; // 팔로워 수
    private int feedNum; // 피드 수
    private List<String> feeds; // 피드 목록 (예: 피드 제목 리스트)

    // User 엔티티로부터 DTO 생성
    public static UserResponseDto convertFromUser(UserEntity user) {
        return UserResponseDto.builder()
                .name(user.getName())
                .intro(user.getIntro())
                .followingNum(user.getFollowingNum())
                .followerNum(user.getFollowerNum())
                .feedNum(user.getFeedNum())
//              .feeds(user.getFeeds())
                .build();
    }
}
