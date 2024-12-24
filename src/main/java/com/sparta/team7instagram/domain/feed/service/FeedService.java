package com.sparta.team7instagram.domain.feed.service;

import com.sparta.team7instagram.domain.feed.dto.request.FeedCreateRequestDto;
import com.sparta.team7instagram.domain.feed.dto.request.FeedUpdateRequestDto;
import com.sparta.team7instagram.domain.feed.dto.response.FeedReadResponseDto;
import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import com.sparta.team7instagram.domain.feed.entity.FeedLikeEntity;
import com.sparta.team7instagram.domain.feed.entity.FeedTagEntity;
import com.sparta.team7instagram.domain.feed.exception.FeedNotFoundException;
import com.sparta.team7instagram.domain.feed.repository.FeedLikeRepository;
import com.sparta.team7instagram.domain.tag.entity.TagEntity;
import com.sparta.team7instagram.domain.feed.repository.FeedRepository;
import com.sparta.team7instagram.domain.tag.service.TagService;
import com.sparta.team7instagram.domain.user.entity.UserEntity;
import com.sparta.team7instagram.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedService {

    private final TagService tagService;
    private final FeedRepository feedRepository;
    private final FeedLikeRepository feedLikeRepository;

    @Transactional
    public Long createFeed(FeedCreateRequestDto request, Long userId) {
        UserEntity user = UserEntity.builder().build();

        // 유저 정보 대조

        FeedEntity feed = request.toEntity(user);

        for (String tagName : request.tags()) {
            TagEntity tag = tagService.findTagByName(tagName);

            FeedTagEntity feedTag = FeedTagEntity.builder()
                    .feed(feed)
                    .tag(tag)
                    .build();

            feed.addFeedTag(feedTag);
        }

        FeedEntity save = feedRepository.save(feed);
        return save.getId();
    }

    public FeedReadResponseDto findFeed(Long feedId) {
        FeedEntity feed = findFeedById(feedId);

        return FeedReadResponseDto.from(feed);
    }

    public Page<FeedReadResponseDto> findAllFeedByConditions(String tag, Pageable pageable, Long userId) {
        UserEntity user = UserEntity.builder().build();

        return feedRepository.findFeedsByConditions(tag, pageable);
    }

    @Transactional
    public void updateFeed(Long feedId, FeedUpdateRequestDto request, Long userId) {
        UserEntity user = UserEntity.builder().build();

        // 유저 정보 대조

        FeedEntity feed = findFeedById(feedId);

        if (!request.tags().isEmpty()) {
            feed.removeAllFeedTag();

            for (String tagName : request.tags()) {
                TagEntity tag = tagService.findTagByName(tagName);

                FeedTagEntity feedTag = FeedTagEntity.builder()
                        .feed(feed)
                        .tag(tag)
                        .build();

                feed.addFeedTag(feedTag);
            }
        }
    }

    @Transactional
    public void deleteFeed(Long feedId, Long userId) {
        UserEntity user = UserEntity.builder().build();

        // 유저 정보 대조

        feedRepository.deleteById(feedId);
    }

    @Transactional
    public void createFeedLike(Long feedId, Long userId) {
        UserEntity user = UserEntity.builder().build();
        // 유저 정보 대조
        FeedEntity feed = findFeedById(feedId);

        FeedLikeEntity feedLike = FeedLikeEntity.builder()
                .feed(feed)
                .user(user)
                .build();

        feed.addFeedLike(feedLike);
    }

    @Transactional
    public void deleteFeedLike(Long feedId, Long userId) {
        UserEntity user = UserEntity.builder().build();
        // 유저 정보 대조
        FeedEntity feed = findFeedById(feedId);

        FeedLikeEntity feedLike = FeedLikeEntity.builder()
                .feed(feed)
                .user(user)
                .build();

        feed.removeFeedLike(feedLike);
        feedLikeRepository.delete(feedLike);
    }

    private FeedEntity findFeedById(Long feedId) {
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new FeedNotFoundException(ErrorCode.FEED_NOT_FOUND));
    }
}
