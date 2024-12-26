package com.sparta.team7instagram.domain.user.entity;

import com.sparta.team7instagram.domain.common.BaseEntity;
import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 4)
    private String name;

    @Column(length = 30)
    private String intro;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FollowEntity> following = new ArrayList<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FollowEntity> follower = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedEntity> feeds = new ArrayList<>();

    public UserEntity(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void updateNameAndIntro(String name, String intro) {
        this.name = name;
        this.intro = intro;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

}
