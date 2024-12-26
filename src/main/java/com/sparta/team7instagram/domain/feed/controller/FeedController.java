package com.sparta.team7instagram.domain.feed.controller;

import com.sparta.team7instagram.domain.feed.dto.request.FeedCreateRequestDto;
import com.sparta.team7instagram.domain.feed.dto.request.FeedUpdateRequestDto;
import com.sparta.team7instagram.domain.feed.dto.response.FeedReadResponseDto;
import com.sparta.team7instagram.domain.feed.service.FeedService;
import com.sparta.team7instagram.global.util.SessionUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping
    public ResponseEntity<Void> createFeed(
            @RequestBody @Valid final FeedCreateRequestDto request,
            @SessionAttribute(name = SessionUtil.SESSION_KEY) final Long loginUserId
    ) {
        final Long feedId = feedService.createFeed(request, loginUserId);

        final URI uri = UriComponentsBuilder.fromPath("/feeds/{feedId}")
                .buildAndExpand(feedId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<FeedReadResponseDto>> readFeeds(
            @RequestParam(required = false) final String searchTag,
            @RequestParam(required = false) final String sort,
            @RequestParam(required = false) final String startDate,
            @RequestParam(required = false) final String endDate,
            Pageable pageable,
            @SessionAttribute(name = SessionUtil.SESSION_KEY) final Long loginUserId
    ) {
        return ResponseEntity.ok(feedService.findAllFeedByConditions(searchTag, sort, startDate, endDate, pageable, loginUserId));
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<FeedReadResponseDto> readFeed(
            @PathVariable final Long feedId
    ) {
        return ResponseEntity.ok(feedService.findFeed(feedId));
    }

    @PatchMapping("/{feedId}")
    public ResponseEntity<Void> updateFeed(
            @PathVariable final Long feedId,
            @RequestBody final FeedUpdateRequestDto request,
            @SessionAttribute(name = SessionUtil.SESSION_KEY) final Long loginUserId
    ) {
        feedService.updateFeed(feedId, request, loginUserId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity<FeedReadResponseDto> deleteFeed(
            @PathVariable final Long feedId,
            @SessionAttribute(name = SessionUtil.SESSION_KEY) final Long loginUserId
    ) {
        feedService.deleteFeed(feedId, loginUserId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{feedId}/likes")
    public ResponseEntity<Void> createFeedLike(
            @PathVariable final Long feedId,
            @SessionAttribute(name = SessionUtil.SESSION_KEY) final Long loginUserId
    ) {
        feedService.createFeedLike(feedId, loginUserId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{feedId}/likes")
    public ResponseEntity<Void> deleteFeedLike(
            @PathVariable final Long feedId,
            @SessionAttribute(name = SessionUtil.SESSION_KEY) final Long loginUserId
    ) {
        feedService.deleteFeedLike(feedId, loginUserId);

        return ResponseEntity.ok().build();
    }
}
