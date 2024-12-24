package com.sparta.team7instagram.domain.feed.exception;

public class FeedNotFoundException extends RuntimeException {
    public FeedNotFoundException(String msg) {
        super(msg);
    }
}
