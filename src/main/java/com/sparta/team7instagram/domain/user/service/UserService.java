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
            Long usedId
    ) {
        UserEntity user = userRepository.findById(usedId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        return UserResponseDto.convertFromUser(user);
    }

    /**
     * 유저 프로필 검색
     */
    @Transactional(readOnly = true)
    public List<UserResponseDto> searchUsersByName(
            String name
    ) {
        List<UserEntity> users = userRepository.findByNameContaining(name);
        // Entity를 DTO로 변환
        return users.stream()
                .map(UserResponseDto::convertFromUser)
                .collect(Collectors.toList());
    }

    /**
     * 유저 수정
     */
    @Transactional
    public void updateUser(
            Long usedId,
            UserUpdateRequestDto userUpdateRequestDto
    ) {
        UserEntity user = userRepository.findById(usedId).orElseThrow(()
                -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        user.updateNameAndIntro(userUpdateRequestDto.getName(), userUpdateRequestDto.getIntro());
    }

    /**
     * 유저 비밀번호 수정
     */
    @Transactional
    public UserResponseDto updatePassword(
            Long usedId,
            UserPasswordUpdateRequestDto userPasswordUpdateRequestDto
    ) {
        UserEntity user = userRepository.findById(usedId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        // 기존 비밀번호 확인
        if (!passwordEncoder.matches(userPasswordUpdateRequestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        // 새 비밀번호 설정
        String encodedPassword = passwordEncoder.encode(userPasswordUpdateRequestDto.getChangedPassword());
        user.updatePassword(encodedPassword);

        return UserResponseDto.convertFromUser(user);
    }

    /**
     * 유저 삭제
     */
    @Transactional
    public void deleteUser(
            Long usedId,
            String password,
            HttpSession session
    ) {
        UserEntity user = userRepository.findById(usedId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.deleteById(usedId);
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
        UserEntity follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우를 시도하는 유저를 찾을 수 없습니다."));
        UserEntity following = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 대상 유저를 찾을 수 없습니다."));
        // 이미 팔로우 관계가 있는지 확인
        if (followRepository.findByFollowerAndFollowing(follower, following).isPresent()) {
            throw new IllegalArgumentException("이미 팔로우 상태입니다.");
        }

        followRepository.save(FollowEntity.builder()
                .follower(follower)
                .following(following)
                .build());

        follower.increaseFollowingNum(); // 내가 팔로잉한 유저 수 증가
        following.increaseFollowerNum(); // 나를 팔로우한 유저 수 증가
    }

    /**
     * 팔로우 취소
     */
    @Transactional
    public void unfollowUser(
            Long followerId,
            Long followingId
    ) {
        UserEntity follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우를 취소하는 유저를 찾을 수 없습니다."));
        UserEntity following = userRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 취소 대상 유저를 찾을 수 없습니다."));

        FollowEntity follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(follow);

        follower.decreaseFollowingNum(); // 내가 팔로잉한 유저 수 감소
        following.decreaseFollowerNum(); // 나를 팔로우한 유저 수 감소
    }
}
