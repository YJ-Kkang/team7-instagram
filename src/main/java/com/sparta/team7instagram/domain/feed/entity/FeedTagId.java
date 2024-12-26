package com.sparta.team7instagram.domain.feed.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class FeedTagId implements Serializable {

    private Long feedId;
    private Long tagId;

    @Builder
    public FeedTagId(Long feedId, Long tagId) {
        this.feedId = feedId;
        this.tagId = tagId;
    }
}
