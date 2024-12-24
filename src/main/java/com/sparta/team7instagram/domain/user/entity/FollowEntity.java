package com.sparta.team7instagram.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "follows")
public class FollowEntity {

    @EmbeddedId
    private FollowId id;
    /**
     * following = 내가 팔로우한 유저
     * follower = 팔로우한 유저
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("following")
    @JoinColumn(name = "following_id", nullable = false)
    private UserEntity following;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("follower")
    @JoinColumn(name = "follower_id", nullable = false)
    private UserEntity follower;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @EqualsAndHashCode
    public static class FollowId implements Serializable {
        @Column(name = "following_id")
        private Long following;
        @Column(name = "follower_id")
        private Long follower;
    }
}
