package com.sparta.team7instagram.domain.feed.repository;

import com.sparta.team7instagram.domain.feed.dto.response.FeedReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedCustomRepository {
    Page<FeedReadResponseDto> findFeedsByConditions(String tag, Pageable pageable);
}
