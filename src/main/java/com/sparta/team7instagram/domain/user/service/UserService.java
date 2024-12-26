package com.sparta.team7instagram.domain.user.service;

import com.sparta.team7instagram.domain.auth.exception.InvalidPasswordException;
import com.sparta.team7instagram.domain.user.dto.request.UserPasswordUpdateRequestDto;
import com.sparta.team7instagram.domain.user.dto.request.UserUpdateRequestDto;
import com.sparta.team7instagram.domain.user.dto.response.UserResponseDto;
import com.sparta.team7instagram.domain.user.dto.response.UserSearchResponseDto;
import com.sparta.team7instagram.domain.user.entity.DeletedUserEntity;
import com.sparta.team7instagram.domain.user.entity.FollowEntity;
import com.sparta.team7instagram.domain.user.entity.UserEntity;
import com.sparta.team7instagram.domain.auth.config.PasswordEncoder;
import com.sparta.team7instagram.domain.user.exception.InvalidFollowException;
import com.sparta.team7instagram.domain.user.exception.UserNotExistingException;
import com.sparta.team7instagram.domain.user.repository.DeletedUserRepository;
import com.sparta.team7instagram.domain.user.repository.FollowRepository;
import com.sparta.team7instagram.domain.user.repository.UserRepository;
import com.sparta.team7instagram.global.exception.error.ErrorCode;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;

    private final DeletedUserRepository deletedUserRepository;

    @Transactional(readOnly = true)
    public UserResponseDto getUserProfile(
            Long userId
    ) {
        UserEntity user = findById(userId);
        return UserResponseDto.from(user);
    }

    @Transactional(readOnly = true)
    public List<UserSearchResponseDto> searchUsersByName(
            String name
    ) {
        List<UserEntity> users = userRepository.findByNameContaining(name);

        return users.stream()
                .map(UserSearchResponseDto::from)
                .toList();
    }

    @Transactional
    public void updateUser(
            Long userId,
            UserUpdateRequestDto userUpdateRequestDto
    ) {
        UserEntity user = findById(userId);
        user.updateNameAndIntro(userUpdateRequestDto.getName(), userUpdateRequestDto.getIntro());
    }

    @Transactional
    public void updatePassword(
            Long userId,
            UserPasswordUpdateRequestDto userPasswordUpdateRequestDto
    ) {
        UserEntity user = findById(userId);
        if (!passwordEncoder.matches(userPasswordUpdateRequestDto.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD);
        }
        if (passwordEncoder.matches(userPasswordUpdateRequestDto.getChangedPassword(), user.getPassword())) {
            throw new InvalidPasswordException(ErrorCode.SAME_PASSWORD);
        }

        String encodedPassword = passwordEncoder.encode(userPasswordUpdateRequestDto.getChangedPassword());
        user.updatePassword(encodedPassword);
    }

    @Transactional
    public void deleteUser(
            Long userId,
            String password,
            HttpSession session
    ) {
        UserEntity user = findById(userId);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD);
        }

        DeletedUserEntity deletedUser = new DeletedUserEntity(user.getEmail());
        deletedUserRepository.save(deletedUser);

        userRepository.deleteById(userId);
        session.invalidate();
    }

    @Transactional
    public void followUser(
            Long followerId,
            Long followingId
    ) {
        UserEntity follower = findById(followerId);
        UserEntity following = findById(followingId);
        if (followerId.equals(followingId)) {
            throw new InvalidFollowException(ErrorCode.NOT_SELF_FOLLOW);
        }
        if (followRepository.findByFollowerAndFollowing(follower, following).isPresent()) {
            throw new InvalidFollowException(ErrorCode.EXISTING_FOLLOW);
        }
        FollowEntity.FollowId followId = new FollowEntity.FollowId(followingId, followerId);

        followRepository.save(FollowEntity.builder()
                .id(followId)
                .follower(follower)
                .following(following)
                .build());
    }

    @Transactional
    public void unfollowUser(
            Long followerId,
            Long followingId
    ) {
        UserEntity follower = findById(followerId);
        UserEntity following = findById(followingId);
        FollowEntity follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new InvalidFollowException(ErrorCode.NOT_FOLLOWING));

        followRepository.delete(follow);
    }

    @Transactional(readOnly = true)
    public UserEntity findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistingException(ErrorCode.USER_NOT_FOUND));
    }
}