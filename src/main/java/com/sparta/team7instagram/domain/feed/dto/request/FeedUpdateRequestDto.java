package com.sparta.team7instagram.domain.feed.dto.request;

import java.util.Set;

public record FeedUpdateRequestDto(
        String content,
        Set<String> tags
) {
}
