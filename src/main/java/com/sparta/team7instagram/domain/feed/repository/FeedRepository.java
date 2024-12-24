package com.sparta.team7instagram.domain.feed.repository;

import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
}
