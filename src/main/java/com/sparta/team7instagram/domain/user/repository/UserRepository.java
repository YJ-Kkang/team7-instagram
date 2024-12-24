package com.sparta.team7instagram.domain.user.repository;

import com.sparta.team7instagram.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByNameContaining(String name);

    boolean existsByEmail(String email);
}
