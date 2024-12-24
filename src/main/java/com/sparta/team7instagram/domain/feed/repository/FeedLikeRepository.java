package com.sparta.team7instagram.domain.feed.repository;

import com.sparta.team7instagram.domain.feed.entity.FeedLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLikeEntity, Long> {
}
