package com.sparta.team7instagram.domain.feed.dto.response;

import com.sparta.team7instagram.domain.tag.entity.TagEntity;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record TagResponseDTO(
        Long tagId,
        String tagName
) {

    public static TagResponseDTO from(TagEntity tag) {
        return TagResponseDTO.builder()
                .tagId(tag.getId())
                .tagName(tag.getName())
                .build();
    }
}
