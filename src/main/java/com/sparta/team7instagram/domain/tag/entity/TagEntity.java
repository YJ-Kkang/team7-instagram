package com.sparta.team7instagram.domain.tag.entity;

import com.sparta.team7instagram.domain.feed.entity.FeedTagEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<FeedTagEntity> feedTags = new ArrayList<>();

    @Builder
    public TagEntity(Long id, String name, List<FeedTagEntity> feedTags) {
        this.id = id;
        this.name = name;
        this.feedTags = (feedTags == null) ? new ArrayList<>() : feedTags;
    }

    public void addFeedTag(FeedTagEntity feedTag) {
        this.feedTags.add(feedTag);
        if (feedTag.getTag() != this) {
            feedTag.setTag(this);
        }
    }
}
