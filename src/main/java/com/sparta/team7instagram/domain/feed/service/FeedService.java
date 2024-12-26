package com.sparta.team7instagram.domain.feed.service;

import com.sparta.team7instagram.domain.auth.service.AuthService;
import com.sparta.team7instagram.domain.feed.dto.request.FeedCreateRequestDto;
import com.sparta.team7instagram.domain.feed.dto.request.FeedUpdateRequestDto;
import com.sparta.team7instagram.domain.feed.dto.response.FeedReadResponseDto;
import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import com.sparta.team7instagram.domain.feed.entity.FeedLikeEntity;
import com.sparta.team7instagram.domain.feed.entity.FeedTagEntity;
import com.sparta.team7instagram.domain.feed.exception.FeedNotFoundException;
import com.sparta.team7instagram.domain.tag.entity.TagEntity;
import com.sparta.team7instagram.domain.feed.repository.FeedRepository;
import com.sparta.team7instagram.domain.tag.service.TagService;
import com.sparta.team7instagram.domain.user.entity.UserEntity;
import com.sparta.team7instagram.domain.user.repository.FollowRepository;
import com.sparta.team7instagram.domain.user.repository.UserRepository;
import com.sparta.team7instagram.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedService {

    private final TagService tagService;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final FollowRepository followRepository;

    @Transactional
    public Long createFeed(FeedCreateRequestDto request, Long userId) {
        // userService 사용 필요
        UserEntity user = userRepository.findById(userId).get();
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

    public Page<FeedReadResponseDto> findAllFeedByConditions(
            String tagName,
            String sort,
            String startDate,
            String endDate,
            Pageable pageable,
            Long userId
    ) {
        List<Long> followingIds = followRepository.findFollowingIdsByFollowerId(userId);

        return feedRepository.findFeedsByConditions(
                tagName,
                sort,
                dateFormatter(startDate),
                dateFormatter(endDate),
                pageable,
                followingIds);
    }

    @Transactional
    public void updateFeed(Long feedId, FeedUpdateRequestDto request, Long userId) {
        // 유저 필요
        UserEntity user = userRepository.findById(userId).get();
        FeedEntity feed = findFeedById(feedId);

        authService.isSameUsers(feed.getUser().getId(), user.getId());

        if (request.tags() != null) {
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

        feed.updateContent(request.content());
    }

    @Transactional
    public void deleteFeed(Long feedId, Long userId) {
        // 유저 필요
        UserEntity user = userRepository.findById(userId).get();
        FeedEntity feed = findFeedById(feedId);

        authService.isSameUsers(feed.getUser().getId(), user.getId());

        feedRepository.deleteById(feedId);
    }

    @Transactional
    public void createFeedLike(Long feedId, Long userId) {
        // 유저 필요
        UserEntity user = userRepository.findById(userId).get();
        FeedEntity feed = findFeedById(feedId);

        authService.isSameUsers(feed.getUser().getId(), user.getId());

        FeedLikeEntity feedLike = FeedLikeEntity.builder()
                .feed(feed)
                .user(user)
                .build();

        feed.addFeedLike(feedLike);
    }

    @Transactional
    public void deleteFeedLike(Long feedId, Long userId) {
        // 유저 필요
        UserEntity user = userRepository.findById(userId).get();
        FeedEntity feed = findFeedById(feedId);

        authService.isSameUsers(feed.getUser().getId(), user.getId());

        FeedLikeEntity feedLike = feed.getFeedLikes().stream()
                .filter(like -> like.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new FeedNotFoundException(ErrorCode.FEED_LIKE_NOT_FOUND));

        feed.removeFeedLike(feedLike);
    }

    private FeedEntity findFeedById(Long feedId) {
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new FeedNotFoundException(ErrorCode.FEED_NOT_FOUND));
    }

    private LocalDate dateFormatter(String date) {
        return date != null ? LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }
}