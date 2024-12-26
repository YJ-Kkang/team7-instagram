package com.sparta.team7instagram.domain.feed.repository;

import com.sparta.team7instagram.domain.feed.dto.response.FeedReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface FeedCustomRepository {
    Page<FeedReadResponseDto> findFeedsByConditions(String tagName, String sort, LocalDate startDate, LocalDate endDate, Pageable pageable, List<Long> followingIds);
}
