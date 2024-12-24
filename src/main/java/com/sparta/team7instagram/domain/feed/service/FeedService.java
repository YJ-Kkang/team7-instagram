package com.sparta.team7instagram.domain.feed.service;

import com.sparta.team7instagram.domain.auth.Entity.User;
import com.sparta.team7instagram.domain.feed.dto.request.FeedCreateRequestDTO;
import com.sparta.team7instagram.domain.feed.dto.request.FeedUpdateRequestDTO;
import com.sparta.team7instagram.domain.feed.dto.response.FeedReadResponseDTO;
import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import com.sparta.team7instagram.domain.feed.entity.FeedLikeEntity;
import com.sparta.team7instagram.domain.feed.entity.FeedTagEntity;
import com.sparta.team7instagram.domain.feed.exception.FeedNotFoundException;
import com.sparta.team7instagram.domain.tag.entity.TagEntity;
import com.sparta.team7instagram.domain.feed.repository.FeedRepository;
import com.sparta.team7instagram.domain.tag.service.TagService;
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
    public Long createFeed(FeedCreateRequestDTO request, Long userId) {
        User user = new User();

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

    public FeedReadResponseDTO findFeed(Long feedId) {
        FeedEntity feed = findFeedById(feedId);

        return FeedReadResponseDTO.from(feed);
    }

    public Page<FeedReadResponseDTO> findAllFeedByConditions(String tag, Pageable pageable, Long userId) {
        User user = new User();

        return feedRepository.findFeedsByConditions(tag, pageable);
    }

    @Transactional
    public void updateFeed(Long feedId, FeedUpdateRequestDTO request, Long userId) {
        User user = new User();

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
        User user = new User();

        // 유저 정보 대조

        feedRepository.deleteById(feedId);
    }

    @Transactional
    public void createFeedLike(Long feedId, Long userId) {
        User user = new User();
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
        User user = new User();
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
                .orElseThrow(() -> new FeedNotFoundException("피드가 존재하지 않습니다."));
    }
}
