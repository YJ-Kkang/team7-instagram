package com.sparta.team7instagram.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 유저 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {
    // 속성
    /**
     * 유저 식별자, 이메일, 비밀번호, 유저명, 소개글, 피드 수, 피드 목록, 팔로워 수, 팔로잉 수
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // 유저 식별자

    @Column(nullable = false, unique = true) // 중복 금지
    private String email; // 이메일

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false, length = 4)
    private String name; // 유저명

    @Column(length = 30) // null 가능, 30글자 이하
    private String intro; // 소개글

    private int followingNum; // 팔로잉 수 -> 내가 친구로 추가한 유저

    private int followerNum; // 팔로워 수 -> 나를 친구로 추가한 유저

    private int feedNum; // 피드 수

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> following = new ArrayList<>(); // 내가 팔로우한 유저 목록

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followers = new ArrayList<>(); // 나를 팔로우한 유저 목록

    private List<String> feeds; // 피드 목록

    // 유저명, 소개글 수정 메서드
    public void updateNameAndIntro(String name, String intro) {
        this.name = name;
        this.intro = intro;
    }

    // 비밀번호 수정 메서드
    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    // 내가 팔로잉한 유저 수 증가 메서드
    public void increaseFollowingNum() {
        this.followingNum++;
    }

    // 내가 팔로잉한 유저 수 감소 메서드
    public void decreaseFollowingNum() {
        this.followingNum--;
    }

    // 나를 팔로우한 유저 수 증가 메서드
    public void increaseFollowerNum() {
        this.followerNum++;
    }

    // 나를 팔로우한 유저 수 감소 메서드
    public void decreaseFollowerNum() {
        this.followerNum--;
    }
}
