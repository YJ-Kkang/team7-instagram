package com.sparta.team7instagram.domain.feed.dto.request;

import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import com.sparta.team7instagram.domain.user.entity.UserEntity;

import java.util.Set;

public record FeedUpdateRequestDto(
        String content,
        Set<String> tags
) {

    public FeedEntity toEntity(UserEntity user) {
        return FeedEntity.builder()
                .content(this.content)
                .user(user)
                .build();
    }
}
