package com.sparta.team7instagram.domain.feed.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.team7instagram.domain.feed.dto.FeedReadResponseDtoConvert;
import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import com.sparta.team7instagram.domain.feed.entity.FeedTagEntity;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record FeedReadResponseDto(
        Long feedId,
        String content,
        List<TagResponseDto> tags,
        Long userId,
        String userName,
        Integer feedLikeCount,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt
) {

    public static FeedReadResponseDto from(FeedEntity feed) {
        return FeedReadResponseDto.builder()
                .feedId(feed.getId())
                .content(feed.getContent())
                .tags(get(feed.getFeedTags()))
                .userId(feed.getUser().getId())
                .userName(feed.getUser().getName())
                .feedLikeCount(feed.getFeedLikes().size())
                .createdAt(feed.getCreatedAt())
                .updatedAt(feed.getUpdatedAt())
                .build();
    }

    public static FeedReadResponseDto from(FeedReadResponseDtoConvert dto) {
        return FeedReadResponseDto.builder()
                .feedId(dto.getFeedId())
                .content(dto.getContent())
                .tags(new ArrayList<>())
                .userId(dto.getUserId())
                .userName(dto.getUserName())
                .feedLikeCount(dto.getFeedLikeCount())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    private static List<TagResponseDto> get(List<FeedTagEntity> feedTags) {
        return feedTags.stream()
                .map(feedTag -> TagResponseDto.from(feedTag.getTag()))
                .toList();
    }
}
