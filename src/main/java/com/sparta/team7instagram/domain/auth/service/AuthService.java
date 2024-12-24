package com.sparta.team7instagram.domain.auth.service;

import com.sparta.team7instagram.domain.auth.config.PasswordEncoder;
import com.sparta.team7instagram.domain.auth.dto.LoginUserRequestDto;
import com.sparta.team7instagram.domain.auth.dto.SignupUserRequestDto;
import com.sparta.team7instagram.domain.user.entity.UserEntity;
import com.sparta.team7instagram.domain.user.repository.UserRepository;
import com.sparta.team7instagram.global.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long saveUser(
            SignupUserRequestDto requestDto
    ) {
        checkEmailDuplicateAndThrow(requestDto.email());

        String encodedPassword = passwordEncoder.encode(requestDto.password());

        UserEntity userEntity = new UserEntity(requestDto.email(),encodedPassword, requestDto.name());
        UserEntity createdUser = userRepository.save(userEntity);

        return createdUser.getId();
    }

    public void login(
            LoginUserRequestDto requestDto,
            HttpServletRequest request
    ) {
        UserEntity findUserEntity = userRepository.findByEmail(requestDto.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist email = " + requestDto.email()));
        if(!passwordEncoder.matches(requestDto.password(), findUserEntity.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "wrong password");
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
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return true;
    }

    /**
     * Email 중복 체크
     * @param email 중복 체크할 email
     */
    public void checkEmailDuplicateAndThrow(String email){
        if(userRepository.existsByEmail(email)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"이미 존재하는 이메일입니다.");
        }
    }
}
