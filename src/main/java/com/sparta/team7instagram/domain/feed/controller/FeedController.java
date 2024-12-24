package com.sparta.team7instagram.domain.feed.controller;

import com.sparta.team7instagram.domain.feed.dto.request.FeedCreateRequestDto;
import com.sparta.team7instagram.domain.feed.dto.request.FeedUpdateRequestDto;
import com.sparta.team7instagram.domain.feed.dto.response.FeedReadResponseDto;
import com.sparta.team7instagram.domain.feed.service.FeedService;
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
            Long userId
    ) {
        final Long feedId = feedService.createFeed(request, userId);

        final URI uri = UriComponentsBuilder.fromPath("/feeds/{feedId}")
                .buildAndExpand(feedId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<FeedReadResponseDto>> readFeeds(
            @RequestParam(required = false) final String tagName,
            Pageable pageable,
            Long userId
    ) {
        return ResponseEntity.ok(feedService.findAllFeedByConditions(tagName, pageable, userId));
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
            final Long userId
    ) {
        feedService.updateFeed(feedId, request, userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity<FeedReadResponseDto> deleteFeed(
            @PathVariable final Long feedId,
            final Long userId
    ) {
        feedService.deleteFeed(feedId, userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{feedId}/likes")
    public ResponseEntity<Void> createFeedLike(
            @PathVariable final Long feedId,
            Long userId
    ) {
        feedService.createFeedLike(feedId, userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{feedId}/likes")
    public ResponseEntity<Void> deleteFeedLike(
            @PathVariable final Long feedId,
            Long userId
    ) {
        feedService.deleteFeedLike(feedId, userId);

        return ResponseEntity.ok().build();
    }
}
