package com.sparta.team7instagram.domain.feed.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class FeedLikeId implements Serializable {

    private Long userId;
    private Long feedId;

    @Builder
    public FeedLikeId(Long userId, Long feedId) {
        this.userId = userId;
        this.feedId = feedId;
    }
}