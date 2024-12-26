package com.sparta.team7instagram.domain.comment.service;

import com.sparta.team7instagram.domain.comment.entity.CommentLikeEntity;
import com.sparta.team7instagram.domain.comment.exception.CommentLikeNotFoundException;
import com.sparta.team7instagram.domain.comment.exception.CommentNotFound;
import com.sparta.team7instagram.domain.comment.exception.DuplicateCommentLike;
import com.sparta.team7instagram.domain.comment.exception.SelfCommentLikeNotAllowedException;
import com.sparta.team7instagram.domain.comment.repository.CommentLikeRepository;
import com.sparta.team7instagram.domain.feed.entity.FeedEntity;
import com.sparta.team7instagram.domain.feed.exception.FeedNotFoundException;
import com.sparta.team7instagram.domain.feed.repository.FeedRepository;
import com.sparta.team7instagram.domain.user.repository.UserRepository;
import com.sparta.team7instagram.domain.comment.dto.request.CommentRequestDto;
import com.sparta.team7instagram.domain.comment.dto.response.CommentResponseDto;
import com.sparta.team7instagram.domain.comment.entity.CommentEntity;
import com.sparta.team7instagram.domain.comment.repository.CommentRepository;
import com.sparta.team7instagram.domain.user.entity.UserEntity;
import com.sparta.team7instagram.global.exception.CustomRuntimeException;
import com.sparta.team7instagram.global.exception.UnauthorizedException;
import com.sparta.team7instagram.global.exception.error.ErrorCode;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final FeedRepository feedRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public void createComment(Long userId, CommentRequestDto dto){
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));
        FeedEntity feed = feedRepository.findById(dto.getFeedId()).orElseThrow(() -> new FeedNotFoundException(ErrorCode.FEED_NOT_FOUND));
        String content = dto.getContent();

        CommentEntity commentEntity = CommentEntity.create(user, feed, content);

        commentRepository.save(commentEntity);
    }

    public List<CommentResponseDto> findAll(Long feedId){
        List<CommentEntity> comments = commentRepository.findByFeedId(feedId);

        return comments.stream()
                .map(comment -> CommentResponseDto.toDto(comment.getUser(), comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(Long userId, CommentRequestDto dto, Long commentId){
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));

        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFound(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(userId) || !comment.getFeed().getUser().getId().equals(userId)) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        }

        comment.updateComment(dto.getContent());

        return CommentResponseDto.toDto(user, comment);
    }

    @Transactional
    public void delete(Long userId, Long commentId){

        userRepository.findById(userId).orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));

        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFound(ErrorCode.COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(userId) || !comment.getFeed().getUser().getId().equals(userId)) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public void createLike(Long commentId, Long userId) {

        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFound(ErrorCode.COMMENT_NOT_FOUND));


        if (comment.getUser().getId().equals(userId)) {
            throw new SelfCommentLikeNotAllowedException(ErrorCode.SELF_COMMENT_LIKE_NOT_ALLOWED);
        }

        System.out.println(comment.getUser().getId());
        System.out.println(userId);

        boolean isDuplicate = comment.getCommentLikes().stream()
                .anyMatch(commentLikeEntity -> commentLikeEntity.getUser().getId().equals(userId));

        System.out.println(isDuplicate);
        System.out.println("콘솔 로그 확인용");

        if (isDuplicate) {
            throw new DuplicateCommentLike(ErrorCode.DUPLICATE_COMMENT_LIKE);
        }

        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));
        comment.getCommentLikes().add(new CommentLikeEntity(user,comment));
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteLike(Long userId, Long commentLikeId) {
        CommentLikeEntity commentLike = commentLikeRepository.findById(commentLikeId).orElseThrow(() -> new CommentLikeNotFoundException(ErrorCode.COMMENT_LIKE_NOT_FOUND));

        if (!commentLike.getUser().getId().equals(userId)) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        }

        CommentEntity comment = commentLike.getComment();
        comment.getCommentLikes().remove(commentLike);

        commentLikeRepository.delete(commentLike);
    }
}