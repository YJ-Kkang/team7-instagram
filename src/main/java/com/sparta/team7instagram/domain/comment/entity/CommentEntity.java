package com.sparta.team7instagram.domain.comment.entity;

import com.sparta.team7instagram.domain.common.BaseEntity;
import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import com.sparta.team7instagram.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comments")
@Getter
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    private FeedEntity feed;

    private String content;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLikeEntity> commentLikes;

    public static CommentEntity create(UserEntity user, FeedEntity feed, String content){
        return new CommentEntity(
                user, feed, content
        );
    }

    private CommentEntity(UserEntity user, FeedEntity feed, String content){
        this.user = user;
        this.feed = feed;
        this.content = content;
    }

    public void updateComment(String content){
        this.content = content;
    }
}
