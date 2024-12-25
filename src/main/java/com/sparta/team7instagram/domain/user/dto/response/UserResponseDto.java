package com.sparta.team7instagram.domain.user.dto.response;

import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import com.sparta.team7instagram.domain.user.entity.UserEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private String name;
    private String intro;
    private int followingNum;
    private int followerNum;
    private int feedNum;
    private List<String> feeds;

    public static UserResponseDto UserProfile(UserEntity user) {
        List<String> feedTitles = user.getFeeds().stream()
                .map(FeedEntity::getContent)
                .collect(Collectors.toList());
        return UserResponseDto.builder()
                .name(user.getName())
                .intro(user.getIntro())
                .followingNum(user.getFollowing().size())
                .followerNum(user.getFollower().size())
                .feedNum(user.getFeeds().size())
                .feeds(feedTitles)
                .build();
    }

}
