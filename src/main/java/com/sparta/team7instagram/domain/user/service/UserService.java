package com.sparta.team7instagram.domain.user.service;

import com.sparta.team7instagram.domain.user.dto.request.UserPasswordUpdateRequestDto;
import com.sparta.team7instagram.domain.user.dto.request.UserUpdateRequestDto;
import com.sparta.team7instagram.domain.user.dto.response.UserResponseDto;
import com.sparta.team7instagram.domain.user.entity.FollowEntity;
import com.sparta.team7instagram.domain.user.entity.UserEntity;
import com.sparta.team7instagram.domain.auth.config.PasswordEncoder;
import com.sparta.team7instagram.domain.auth.dto.LoginUserRequestDto;
import com.sparta.team7instagram.domain.auth.dto.SignupUserRequestDto;
import com.sparta.team7instagram.domain.user.repository.FollowRepository;
import com.sparta.team7instagram.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(
            SignupUserRequestDto requestDto
    ) {
        Optional<UserEntity> findUser = userRepository.findByEmail(requestDto.getEmail());

        if(findUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"이미 존재하는 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        UserEntity userEntity = new UserEntity(requestDto.getEmail(),encodedPassword, requestDto.getName());

        userRepository.save(userEntity);
    }

    public void login(
            LoginUserRequestDto requestDto,
            HttpServletRequest request
    ) {
        UserEntity findUserEntity = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist email = " + requestDto.getEmail()));
        if(!passwordEncoder.matches(requestDto.getPassword(), findUserEntity.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "wrong password");
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("userId", findUserEntity.getId());
    }

    /**
     * 유저 프로필 조회
     */
    @Transactional(readOnly = true)
    public UserResponseDto getUserProfile(
            Long userId
    ) {
        UserEntity user = findById(userId);

        return UserResponseDto.builder()
                .name(user.getName())
                .intro(user.getIntro())
                .followingNum(user.getFollowing().size())
                .followerNum(user.getFollower().size())
//              .feedNum
//              .feeds
                .build();
    }

    /**
     * 유저 이름 검색
     */
    @Transactional(readOnly = true)
    public List<String> searchUsersByName(
            String name
    ) {
        List<UserEntity> users = userRepository.findByNameContaining(name);

        return users.stream()
                .map(UserEntity::getName)
                .collect(Collectors.toList());
    }

    /**
     * 유저 수정
     */
    @Transactional
    public void updateUser(
            Long userId,
            UserUpdateRequestDto userUpdateRequestDto
    ) {
        UserEntity user = findById(userId);
        user.updateNameAndIntro(userUpdateRequestDto.getName(), userUpdateRequestDto.getIntro());
    }

    /**
     * 유저 비밀번호 수정
     */
    @Transactional
    public void updatePassword(
            Long userId,
            UserPasswordUpdateRequestDto userPasswordUpdateRequestDto
    ) {
        UserEntity user = findById(userId);
        if (!passwordEncoder.matches(userPasswordUpdateRequestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(userPasswordUpdateRequestDto.getChangedPassword());
        user.updatePassword(encodedPassword);
    }

    /**
     * 유저 삭제
     */
    @Transactional
    public void deleteUser(
            Long userId,
            String password,
            HttpSession session
    ) {
        UserEntity user = findById(userId);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.deleteById(userId);
        session.invalidate();
    }

    /**
     * 팔로우 기능
     */
    @Transactional
    public void followUser(
            Long followerId,
            Long followingId
    ) {
        UserEntity follower = findById(followerId);
        UserEntity following = findById(followingId);
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }
        if (followRepository.findByFollowerAndFollowing(follower, following).isPresent()) {
            throw new IllegalArgumentException("이미 팔로우 상태입니다.");
        }
        FollowEntity.FollowId followId = new FollowEntity.FollowId(followingId, followerId);

        followRepository.save(FollowEntity.builder()
                .id(followId)
                .follower(follower)
                .following(following)
                .build());
    }

    /**
     * 팔로우 취소
     */
    @Transactional
    public void unfollowUser(
            Long followerId,
            Long followingId
    ) {
        UserEntity follower = findById(followerId);
        UserEntity following = findById(followingId);
        FollowEntity follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new IllegalArgumentException("이미 팔로우 상태가 아닙니다."));

        followRepository.delete(follow);
    }

    @Transactional(readOnly = true)
    public UserEntity findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }
}
