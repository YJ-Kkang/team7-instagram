package com.sparta.team7instagram.domain.feed.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class FeedReadResponseDtoConvert {
    private Long feedId;
    private String content;
    private Long tagId;
    private String tagName;
    private Long userId;
    private String userName;
    private Integer feedLikeCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
