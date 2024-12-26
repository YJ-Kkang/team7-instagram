package com.sparta.team7instagram.domain.comment.repository;

import com.sparta.team7instagram.domain.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByFeedId(Long feedId);
}
