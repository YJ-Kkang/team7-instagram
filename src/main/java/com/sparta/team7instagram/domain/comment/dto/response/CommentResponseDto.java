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

    private Long commentId;
    private String content;
    private Long userId;
    private String name;
    private Integer commentLikeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponseDto toDto(CommentEntity comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUser().getId())
                .name(comment.getUser().getName())
                .commentLikeCount(comment.getCommentLikes().size())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
