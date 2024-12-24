package com.sparta.team7instagram.domain.user.dto.response;

import com.sparta.team7instagram.domain.user.entity.UserEntity;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private String name;
    private String intro;
    private int followingNum;
    private int followerNum;
    private int feedNum;
    private List<String> feeds;

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
