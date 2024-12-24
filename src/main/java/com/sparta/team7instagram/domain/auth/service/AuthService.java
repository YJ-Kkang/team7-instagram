package com.sparta.team7instagram.domain.auth.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    /**
     * id값으로 같은 유저인지 체크
     * @param compareId
     * @param id
     * @return 같으면 true, 다르면 UNAUTHORIZED 응답
     */
    public boolean isSameUsers(Long compareId, Long id){
        if(!compareId.equals(id)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return true;
    }
}
