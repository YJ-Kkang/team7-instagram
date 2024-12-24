package com.sparta.team7instagram.domain.feed.entity;

import com.sparta.team7instagram.domain.auth.Entity.User;
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
    private User user;

    @ManyToOne
    @MapsId("feedId")
    @JoinColumn(name = "feed_id")
    private FeedEntity feed;

    @Builder
    public FeedLikeEntity(FeedLikeId id, User user, FeedEntity feed) {
        this.id = id;
        this.user = user;
        this.feed = feed;
    }

    public void setFeed(FeedEntity feed) {
        this.feed = feed;
        if (!feed.getFeedLikes().contains(this)) {
            feed.addFeedLike(this);
        }
    }
}
