package com.sparta.team7instagram.domain.comment.controller;

import com.sparta.team7instagram.domain.comment.dto.request.CommentLikeRequestDto;
import com.sparta.team7instagram.domain.comment.dto.request.CommentRequestDto;
import com.sparta.team7instagram.domain.comment.dto.response.CommentResponseDto;
import com.sparta.team7instagram.domain.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    ResponseEntity<Void> createComment(
            HttpServletRequest request,
            @RequestBody CommentRequestDto dto
    ){
        Long userId = (Long) request.getSession().getAttribute("userId");

        commentService.createComment(userId, dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{feedId}")
    public ResponseEntity<List<CommentResponseDto>> findAll(
            @PathVariable Long feedId
    ){
        return ResponseEntity.ok(commentService.findAll(feedId));
    }

    @PatchMapping("/{commentId}")
    ResponseEntity<CommentResponseDto> updateComment(
            HttpServletRequest request,
            @RequestBody CommentRequestDto dto,
            @PathVariable Long commentId
    ){
        Long userId = (Long) request.getSession().getAttribute("userId");
        return ResponseEntity.ok(commentService.updateComment(userId, dto, commentId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            HttpServletRequest request,
            @PathVariable Long commentId
    ){
        Long userId = (Long) request.getSession().getAttribute("userId");

        commentService.delete(userId, commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/likes")
    public ResponseEntity<Void> createLike(
            HttpServletRequest request,
            @RequestBody CommentLikeRequestDto commentId
    ){
        Long userId = (Long) request.getSession().getAttribute("userId");

        commentService.createLike(commentId.getCommentId(), userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/likes")
    public ResponseEntity<Void> deleteLike(
            HttpServletRequest request,
            @RequestParam Long commentLikeId
    ) {
        Long userId = (Long) request.getSession().getAttribute("userId");

        commentService.deleteLike(userId, commentLikeId);

        return ResponseEntity.ok().build();
    }
}
