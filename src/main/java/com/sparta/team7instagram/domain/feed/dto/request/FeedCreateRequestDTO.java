package com.sparta.team7instagram.domain.feed.dto.request;

import com.sparta.team7instagram.domain.auth.Entity.User;
import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record FeedCreateRequestDTO(
        @NotBlank
        String content,
        Set<String> tags
) {

    public FeedEntity toEntity(User user) {
        return FeedEntity.builder()
                .content(this.content)
                .user(user)
                .build();
    }
}
