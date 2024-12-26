package com.sparta.team7instagram.domain.comment.controller;

import com.sparta.team7instagram.domain.comment.dto.request.CommentLikeRequestDto;
import com.sparta.team7instagram.domain.comment.dto.request.CommentRequestDto;
import com.sparta.team7instagram.domain.comment.dto.response.CommentResponseDto;
import com.sparta.team7instagram.domain.comment.service.CommentService;
import com.sparta.team7instagram.global.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
            HttpSession session,
            @RequestBody CommentRequestDto dto
    ){
        Long userId = SessionUtil.getSession(session);

        commentService.createComment(userId, dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll(
            @RequestParam Long feedId
    ){
        return ResponseEntity.ok(commentService.findAll(feedId));
    }

    @PatchMapping("/{commentId}")
    ResponseEntity<CommentResponseDto> updateComment(
            HttpSession session,
            @RequestBody CommentRequestDto dto,
            @PathVariable Long commentId
    ){
        Long userId = SessionUtil.getSession(session);
        return ResponseEntity.ok(commentService.updateComment(userId, dto, commentId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            HttpSession session,
            @PathVariable Long commentId
    ){
        Long userId = SessionUtil.getSession(session);

        commentService.delete(userId, commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/likes")
    public ResponseEntity<Void> createLike(
            HttpSession session,
            @RequestBody CommentLikeRequestDto commentId
    ){
        Long userId = SessionUtil.getSession(session);

        commentService.createLike(commentId.getCommentId(), userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/likes")
    public ResponseEntity<Void> deleteLike(
            HttpSession session,
            @RequestParam Long commentLikeId
    ) {
        Long userId = SessionUtil.getSession(session);

        commentService.deleteLike(userId, commentLikeId);

        return ResponseEntity.ok().build();
    }
}
