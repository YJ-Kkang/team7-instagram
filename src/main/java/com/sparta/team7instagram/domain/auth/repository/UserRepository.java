package com.sparta.team7instagram.domain.auth.repository;

import com.sparta.team7instagram.domain.auth.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
