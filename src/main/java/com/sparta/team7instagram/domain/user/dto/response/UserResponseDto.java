package com.sparta.team7instagram.domain.user.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private String name;
    private String intro;
    private int followingNum;
    private int followerNum;
    private int feedNum;
    private List<String> feeds;

}
