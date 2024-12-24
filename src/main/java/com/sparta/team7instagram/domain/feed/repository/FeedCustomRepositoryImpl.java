package com.sparta.team7instagram.domain.feed.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.team7instagram.domain.feed.dto.response.FeedReadResponseDto;
import com.sparta.team7instagram.domain.feed.dto.response.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.sparta.team7instagram.domain.feed.entity.QFeedEntity.feedEntity;
import static com.sparta.team7instagram.domain.feed.entity.QFeedLikeEntity.feedLikeEntity;
import static com.sparta.team7instagram.domain.feed.entity.QFeedTagEntity.feedTagEntity;
import static com.sparta.team7instagram.domain.tag.entity.QTagEntity.tagEntity;
import static com.sparta.team7instagram.domain.user.entity.QUserEntity.userEntity;

@RequiredArgsConstructor
public class FeedCustomRepositoryImpl implements FeedCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FeedReadResponseDto> findFeedsByConditions(String tagName, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // followingUser.isEmpty

        List<FeedReadResponseDto> feedReadRes = queryFactory.select(
                        Projections.constructor(
                                FeedReadResponseDto.class,
                                feedEntity.id,
                                feedEntity.content,
                                Projections.list(
                                        Projections.constructor(
                                                TagResponseDto.class,
                                                tagEntity.id,
                                                tagEntity.name)
                                ),
                                feedEntity.user.name,
                                feedEntity.feedLikes.size(),
                                feedEntity.createdAt,
                                feedEntity.updatedAt
                        )
                )
                .from(feedEntity)
                .leftJoin(feedEntity.user, userEntity)
                .leftJoin(feedEntity.feedLikes, feedLikeEntity)
                .leftJoin(feedTagEntity.tag, tagEntity)
                .where(builder)
                .orderBy(feedEntity.updatedAt.desc())
                .groupBy(feedEntity.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long feedReadResCount = Optional.ofNullable(queryFactory.select(
                                feedEntity.count()
                        )
                        .from(feedEntity)
                        .leftJoin(feedEntity.user, userEntity)
                        .leftJoin(feedEntity.feedLikes, feedLikeEntity)
                        .leftJoin(feedTagEntity.tag, tagEntity)
                        .where(builder)
                        .fetchOne())
                .orElse(0L);

        return new PageImpl<>(feedReadRes, pageable, feedReadResCount);
    }
}
