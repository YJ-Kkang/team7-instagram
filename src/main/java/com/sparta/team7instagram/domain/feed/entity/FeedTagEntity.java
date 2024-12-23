package com.sparta.team7instagram.domain.feed.entity;

import com.sparta.team7instagram.domain.tag.entity.TagEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feed_tags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedTagEntity {

    @EmbeddedId
    private FeedTagId id;

    @ManyToOne
    @MapsId("feedId")
    @JoinColumn(name = "feed_id")
    private FeedEntity feed;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private TagEntity tag;

    @Builder
    public FeedTagEntity(FeedTagId id, FeedEntity feed, TagEntity tag) {
        this.id = id;
        setFeed(feed);
        setTag(tag);
    }

    public void setFeed(FeedEntity feed) {
        this.feed = feed;
        if (!feed.getFeedTags().contains(this)) {
            feed.addFeedTag(this);
        }
    }

    public void setTag(TagEntity tag) {
        this.tag = tag;
        if (!tag.getFeedTags().contains(this)) {
            tag.addFeedTag(this);
        }
    }
}
