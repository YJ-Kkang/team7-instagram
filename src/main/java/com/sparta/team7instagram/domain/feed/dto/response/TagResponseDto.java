package com.sparta.team7instagram.domain.feed.dto.response;

import com.sparta.team7instagram.domain.tag.entity.TagEntity;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TagResponseDto(
        Long tagId,
        String tagName
) {

    public static TagResponseDto from(TagEntity tag) {
        return TagResponseDto.builder()
                .tagId(tag.getId())
                .tagName(tag.getName())
                .build();
    }
}
