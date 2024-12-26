package com.sparta.team7instagram.domain.auth.service;

import com.sparta.team7instagram.domain.auth.config.PasswordEncoder;
import com.sparta.team7instagram.domain.auth.dto.requestDto.LoginUserRequestDto;
import com.sparta.team7instagram.domain.auth.dto.requestDto.SignupUserRequestDto;
import com.sparta.team7instagram.domain.auth.exception.DifferentUserException;
import com.sparta.team7instagram.domain.auth.exception.EmailNotFoundException;
import com.sparta.team7instagram.domain.auth.exception.ExistingEmailException;
import com.sparta.team7instagram.domain.auth.exception.InvalidPasswordException;
import com.sparta.team7instagram.domain.user.entity.UserEntity;
import com.sparta.team7instagram.domain.user.repository.UserRepository;
import com.sparta.team7instagram.global.exception.error.ErrorCode;
import com.sparta.team7instagram.global.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long saveUser(
            SignupUserRequestDto requestDto
    ){
        checkEmailDuplicateAndThrow(requestDto.email());

        String encodedPassword = passwordEncoder.encode(requestDto.password());

        UserEntity userEntity = new UserEntity(requestDto.email(),encodedPassword, requestDto.name());
        UserEntity createdUser = userRepository.save(userEntity);

        return createdUser.getId();
    }

    public void login(
            LoginUserRequestDto requestDto,
            HttpServletRequest request
    ){
        UserEntity findUserEntity = userRepository.findByEmail(requestDto.email())
                .orElseThrow(() -> new EmailNotFoundException(ErrorCode.EMAIL_NOT_FOUND));

        if(!passwordEncoder.matches(requestDto.password(), findUserEntity.getPassword())){
            throw new InvalidPasswordException(ErrorCode.INVALID_PASSWORD);
        }

        SessionUtil.createSession(findUserEntity.getId(), request);
    }

    /**
     * id값으로 같은 유저인지 체크
     * @param compareId
     * @param id
     * @return 같으면 true, 다르면 UNAUTHORIZED 응답
     */
    public boolean isSameUsers(
            Long compareId,
            Long id
    ){
        if(!compareId.equals(id)){
            throw new DifferentUserException(ErrorCode.DIFFERENT_USER);
        }
        return true;
    }

    /**
     * Email 중복 체크
     * @param email 중복 체크할 email
     */
    public void checkEmailDuplicateAndThrow(String email){
        if(userRepository.existsByEmail(email)){
            throw new ExistingEmailException(ErrorCode.EXISTING_EMAIL);
        }
    }
}
