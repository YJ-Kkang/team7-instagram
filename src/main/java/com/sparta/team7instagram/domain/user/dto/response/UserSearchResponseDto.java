package com.sparta.team7instagram.domain.user.dto.response;

import com.sparta.team7instagram.domain.user.entity.UserEntity;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserSearchResponseDto(
        Long id,
        String name
) {

    public static UserSearchResponseDto from(UserEntity user) {
        return UserSearchResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
