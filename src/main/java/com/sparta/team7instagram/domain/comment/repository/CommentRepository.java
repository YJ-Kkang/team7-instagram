package com.sparta.team7instagram.domain.comment.repository;

import com.sparta.team7instagram.domain.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    default CommentEntity findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Does not exist id = " + id));
    }

    List<CommentEntity> findByFeedId(Long feedId);
}
