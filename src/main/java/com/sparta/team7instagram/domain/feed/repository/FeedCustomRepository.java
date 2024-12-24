package com.sparta.team7instagram.domain.feed.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedCustomRepository {
    Page<FeedReadResponseDTO> findFeedsByConditions(String tag, Pageable pageable);
}
