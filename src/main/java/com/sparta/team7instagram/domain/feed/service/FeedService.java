package com.sparta.team7instagram.domain.feed.service;

import com.sparta.team7instagram.domain.auth.Entity.User;
import com.sparta.team7instagram.domain.feed.dto.request.FeedCreateRequestDTO;
import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import com.sparta.team7instagram.domain.feed.entity.FeedTagEntity;
import com.sparta.team7instagram.domain.tag.entity.TagEntity;
import com.sparta.team7instagram.domain.feed.repository.FeedRepository;
import com.sparta.team7instagram.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedService {

    private final TagService tagService;
    private final FeedRepository feedRepository;

    @Transactional
    public Long createFeed(FeedCreateRequestDTO request, Long userId) {
        // 유저 정보 대조
        User user = new User();

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
}
