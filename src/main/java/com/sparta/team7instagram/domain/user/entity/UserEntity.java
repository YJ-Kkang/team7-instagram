package com.sparta.team7instagram.domain.user.entity;

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
public class UserEntity {
    /**
     * 유저 식별자, 이메일, 비밀번호, 유저명, 소개글, 피드 수, 피드 목록, 팔로워 수, 팔로잉 수
     */
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

    private int followingNum; // 팔로잉 수 -> 내가 친구로 추가한 유저

    private int followerNum; // 팔로워 수 -> 나를 친구로 추가한 유저

    private int feedNum;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FollowEntity> following = new ArrayList<>(); // 내가 팔로우한 유저 목록

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FollowEntity> followers = new ArrayList<>(); // 나를 팔로우한 유저 목록

//  private List<String> feeds; // 피드 목록

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

    public void increaseFollowingNum() {
        this.followingNum++;
    }

    public void decreaseFollowingNum() {
        this.followingNum--;
    }

    public void increaseFollowerNum() {
        this.followerNum++;
    }

    public void decreaseFollowerNum() {
        this.followerNum--;
    }
}
