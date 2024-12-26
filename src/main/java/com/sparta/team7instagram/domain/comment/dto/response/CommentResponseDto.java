package com.sparta.team7instagram.domain.comment.dto.response;

import com.sparta.team7instagram.domain.comment.entity.CommentEntity;
import com.sparta.team7instagram.domain.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class CommentResponseDto {

    private String content;

    private String nickname;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponseDto toDto(UserEntity user, CommentEntity comment) {
        return CommentResponseDto.builder()
                .content(comment.getContent())
                .nickname(user.getName())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
