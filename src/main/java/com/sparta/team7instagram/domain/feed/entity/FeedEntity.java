package com.sparta.team7instagram.domain.feed.entity;

import com.sparta.team7instagram.domain.common.BaseEntity;
import com.sparta.team7instagram.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feeds")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FeedTagEntity> feedTags = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FeedLikeEntity> feedLikes = new ArrayList<>();

    @Builder
    public FeedEntity(Long id, String content, UserEntity user, List<FeedTagEntity> feedTags, List<FeedLikeEntity> feedLikes) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.feedTags = feedTags;
        this.feedLikes = feedLikes;
    }

    public void addFeedTag(FeedTagEntity feedTag) {
        this.feedTags.add(feedTag);
        if (feedTag.getFeed() != this) {
            feedTag.setFeed(this);
        }
    }

    public void addFeedLike(FeedLikeEntity feedLike) {
        this.feedLikes.add(feedLike);
        if (feedLike.getFeed() != this) {
            feedLike.setFeed(this);
        }
    }

    public void removeAllFeedTag() {
        this.feedTags.clear();
    }

    public void removeFeedLike(FeedLikeEntity feedLike) {
        this.feedLikes.remove(feedLike);
    }
}
