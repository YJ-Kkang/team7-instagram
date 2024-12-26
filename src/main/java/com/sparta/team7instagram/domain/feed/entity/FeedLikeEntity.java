package com.sparta.team7instagram.domain.feed.entity;

import com.sparta.team7instagram.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feed_likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedLikeEntity {

    @EmbeddedId
    private FeedLikeId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("feedId")
    @JoinColumn(name = "feed_id")
    private FeedEntity feed;

    @Builder
    public FeedLikeEntity(UserEntity user, FeedEntity feed) {
        this.user = user;
        this.feed = feed;
        this.id = FeedLikeId.builder().userId(user.getId()).feedId(feed.getId()).build();
    }

    public void setFeed(FeedEntity feed) {
        this.feed = feed;
        if (!feed.getFeedLikes().contains(this)) {
            feed.addFeedLike(this);
        }
    }
}