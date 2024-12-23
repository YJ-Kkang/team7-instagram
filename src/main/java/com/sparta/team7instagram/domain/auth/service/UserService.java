package com.sparta.team7instagram.domain.auth.service;

import com.sparta.team7instagram.domain.auth.Entity.User;
import com.sparta.team7instagram.domain.auth.config.PasswordEncoder;
import com.sparta.team7instagram.domain.auth.dto.LoginUserRequestDto;
import com.sparta.team7instagram.domain.auth.dto.SignupUserRequestDto;
import com.sparta.team7instagram.domain.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(SignupUserRequestDto requestDto) {
        Optional<User> findUser = userRepository.findByEmail(requestDto.getEmail());

        if(findUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"이미 존재하는 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getEmail(),encodedPassword, requestDto.getName());

        userRepository.save(user);
    }

    public void login(LoginUserRequestDto requestDto, HttpServletRequest request) {
        User findUser = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist email = " + requestDto.getEmail()));
        if(!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "wrong password");
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("userId", findUser.getId());
    }
}
