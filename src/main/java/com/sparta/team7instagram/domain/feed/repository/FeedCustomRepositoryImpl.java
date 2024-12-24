package com.sparta.team7instagram.domain.feed.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.team7instagram.domain.feed.dto.response.FeedReadResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.sparta.instagram.feed.QFeed.feed;
import static com.sparta.instagram.feed.QFeedLike.feedLike;
import static com.sparta.instagram.feed.QFeedTag.feedTag;
import static com.sparta.instagram.feed.QTag.tag;
import static com.sparta.instagram.feed.QUser.user;

@RequiredArgsConstructor
public class FeedCustomRepositoryImpl implements FeedCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FeedReadResponseDTO> findFeedsByConditions(String tagName, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // followingUser.isEmpty

        List<FeedReadResponseDTO> feedReadRes = queryFactory.select(
                        Projections.constructor(
                                FeedReadResponseDTO.class,
                                feed.id,
                                feed.content,
                                Projections.list(
                                        Projections.constructor(
                                                TagRes.class,
                                                tag.id,
                                                tag.name)
                                ),
                                feed.user.name,
                                feed.feedLikes.size(),
                                feed.createdAt,
                                feed.updatedAt
                        )
                )
                .from(feed)
                .leftJoin(feed.user, user)
                .leftJoin(feed.feedLikes, feedLike)
                .leftJoin(feedTag.tag, tag)
                .where(builder)
                .orderBy(feed.updatedAt.desc())
                .groupBy(feed.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long feedReadResCount = Optional.ofNullable(queryFactory.select(
                                feed.count()
                        )
                        .from(feed)
                        .leftJoin(feed.user, user)
                        .leftJoin(feed.feedLikes, feedLike)
                        .leftJoin(feedTag.tag, tag)
                        .where(builder)
                        .fetchOne())
                .orElse(0L);

        return new PageImpl<>(feedReadRes, pageable, feedReadResCount);
    }
}
