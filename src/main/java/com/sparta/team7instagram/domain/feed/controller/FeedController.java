package com.sparta.team7instagram.domain.feed.controller;

import com.sparta.team7instagram.domain.feed.dto.request.FeedCreateRequestDTO;
import com.sparta.team7instagram.domain.feed.service.FeedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
