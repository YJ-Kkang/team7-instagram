package com.sparta.team7instagram.domain.user.repository;

import com.sparta.team7instagram.domain.user.entity.DeletedUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedUserRepository extends JpaRepository<DeletedUserEntity, Long> {
    boolean existsByEmail(String email);
}
