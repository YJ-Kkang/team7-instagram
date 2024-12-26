package com.sparta.team7instagram.domain.comment.repository;

import com.sparta.team7instagram.domain.comment.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {

}
