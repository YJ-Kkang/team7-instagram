package com.sparta.team7instagram.domain.feed.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import com.sparta.team7instagram.domain.feed.entity.FeedTagEntity;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record FeedReadResponseDTO(
        Long feedId,
        String content,
        List<TagResponseDTO> tags,
        Long userId,
        String userName,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {

    public static FeedReadResponseDTO from(FeedEntity feed) {
        return FeedReadResponseDTO.builder()
                .feedId(feed.getId())
                .content(feed.getContent())
                .tags(get(feed.getFeedTags()))
                .userId(feed.getUser().getId())
                .userName(feed.getUser().getName())
//                .createdAt(feed.getCreatedAt())
//                .updatedAt(feed.getUpdatedAt())
                .build();
    }

    private static List<TagResponseDTO> get(List<FeedTagEntity> feedTags) {
        return feedTags.stream()
                .map(feedTag -> TagResponseDTO.from(feedTag.getTag()))
                .toList();
    }
}
