package com.sparta.team7instagram.domain.feed.controller;

import com.sparta.team7instagram.domain.feed.dto.request.FeedCreateRequestDTO;
import com.sparta.team7instagram.domain.feed.dto.request.FeedUpdateRequestDTO;
import com.sparta.team7instagram.domain.feed.dto.response.FeedReadResponseDTO;
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
            @RequestBody @Valid final FeedCreateRequestDTO request,
            Long userId
    ) {
        final Long feedId = feedService.createFeed(request, userId);

        final URI uri = UriComponentsBuilder.fromPath("/feeds/{feedId}")
                .buildAndExpand(feedId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<FeedReadResponseDTO>> readFeeds(
            @RequestParam(required = false) final String tagName,
            Pageable pageable,
            Long userId
    ) {
        return ResponseEntity.ok(feedService.findAllFeedByConditions(tagName, pageable, userId));
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<FeedReadResponseDTO> readFeed(
            @PathVariable final Long feedId
    ) {
        return ResponseEntity.ok(feedService.findFeed(feedId));
    }

    @PatchMapping("/{feedId}")
    public ResponseEntity<Void> updateFeed(
            @PathVariable final Long feedId,
            @RequestBody final FeedUpdateRequestDTO request,
            final Long userId
    ) {
        feedService.updateFeed(feedId, request, userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity<FeedReadResponseDTO> deleteFeed(
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
