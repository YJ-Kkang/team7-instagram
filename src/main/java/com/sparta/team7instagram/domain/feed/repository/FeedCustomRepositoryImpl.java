package com.sparta.team7instagram.domain.feed.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.team7instagram.domain.feed.dto.FeedReadResponseDtoConvert;
import com.sparta.team7instagram.domain.feed.dto.response.FeedReadResponseDto;
import com.sparta.team7instagram.domain.feed.dto.response.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.*;

import static com.sparta.team7instagram.domain.feed.entity.QFeedEntity.feedEntity;
import static com.sparta.team7instagram.domain.feed.entity.QFeedLikeEntity.feedLikeEntity;
import static com.sparta.team7instagram.domain.feed.entity.QFeedTagEntity.feedTagEntity;
import static com.sparta.team7instagram.domain.tag.entity.QTagEntity.tagEntity;
import static com.sparta.team7instagram.domain.user.entity.QUserEntity.userEntity;

@RequiredArgsConstructor
public class FeedCustomRepositoryImpl implements FeedCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FeedReadResponseDto> findFeedsByConditions(String tagName, String sort, LocalDate startDate, LocalDate endDate, Pageable pageable, List<Long> followingIds) {
        BooleanBuilder builder = new BooleanBuilder();

        if (followingIds != null && !followingIds.isEmpty()) {
            builder.and(feedEntity.user.id.in(followingIds));
        }

        if (startDate != null && endDate != null) {
            builder.and(feedEntity.createdAt.between(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay()));
        }

        if (tagName != null) {
            builder.and(feedEntity.id.in(
                            JPAExpressions.select(feedTagEntity.feed.id)
                                    .from(feedTagEntity)
                                    .leftJoin(feedTagEntity.tag, tagEntity)
                                    .where(tagEntity.name.startsWithIgnoreCase(tagName))
                    )
            );
        }

        OrderSpecifier<?>[] orderSpecifier = {feedEntity.updatedAt.desc()};

        if ("likes".equalsIgnoreCase(sort)) {
            orderSpecifier = new OrderSpecifier[]{feedEntity.feedLikes.size().desc(), feedEntity.updatedAt.desc()};
        }

//        List<FeedReadResponseDto> feedReadRes = queryFactory.selectFrom(feedEntity)
//                .leftJoin(feedEntity.user, userEntity)
//                .leftJoin(feedEntity.feedLikes, feedLikeEntity)
//                .leftJoin(feedEntity.feedTags, feedTagEntity)
//                .leftJoin(feedTagEntity.tag, tagEntity)
//                .where(builder)
//                .orderBy(orderSpecifier)
//                .groupBy(feedEntity.id, tagEntity.id)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch()
//                .stream()
//                .map(FeedReadResponseDto::from).toList();


//        List<FeedReadResponseDto> feedReadRes = queryFactory.select(
//                        Projections.constructor(
//                                FeedReadResponseDto.class,
//                                feedEntity.id,
//                                feedEntity.content,
//                                Projections.list(
//                                        Projections.constructor(
//                                                TagResponseDto.class,
//                                                tagEntity.id,
//                                                tagEntity.name)
//                                ),
//                                feedEntity.user.name,
//                                feedEntity.feedLikes.size(),
//                                feedEntity.createdAt,
//                                feedEntity.updatedAt
//                        )
//                )
//                .from(feedEntity)
//                .leftJoin(feedEntity.user, userEntity)
//                .leftJoin(feedEntity.feedLikes, feedLikeEntity)
//                .leftJoin(feedTagEntity.tag, tagEntity)
//                .where(builder)
//                .orderBy(feedEntity.updatedAt.desc())
//                .groupBy(feedEntity.id)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();

        List<FeedReadResponseDtoConvert> feedReadRaw = queryFactory.select(
                        Projections.constructor(
                                FeedReadResponseDtoConvert.class,
                                feedEntity.id,
                                feedEntity.content,
                                tagEntity.id,
                                tagEntity.name,
                                feedEntity.user.id,
                                feedEntity.user.name,
                                feedEntity.feedLikes.size(),
                                feedEntity.createdAt,
                                feedEntity.updatedAt
                        )
                )
                .from(feedEntity)
                .leftJoin(feedEntity.user, userEntity)
                .leftJoin(feedEntity.feedLikes, feedLikeEntity)
                .leftJoin(feedEntity.feedTags, feedTagEntity)
                .leftJoin(feedTagEntity.tag, tagEntity)
                .where(builder)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Map<Long, FeedReadResponseDto> groupedFeedConvert = new LinkedHashMap<>();

        for (FeedReadResponseDtoConvert raw : feedReadRaw) {
            FeedReadResponseDto feedDto = groupedFeedConvert.computeIfAbsent(raw.getFeedId(), feed -> FeedReadResponseDto.from(raw));

            if (raw.getTagId() != null && raw.getTagName() != null) {
                feedDto.tags().add(new TagResponseDto(raw.getTagId(), raw.getTagName()));
            }
        }

        List<FeedReadResponseDto> groupedFeedsDto = new ArrayList<>(groupedFeedConvert.values());

        Long feedReadResCount = Optional.ofNullable(queryFactory.select(
                                feedEntity.count()
                        )
                        .from(feedEntity)
                        .leftJoin(feedEntity.user, userEntity)
                        .leftJoin(feedEntity.feedLikes, feedLikeEntity)
                        .leftJoin(feedEntity.feedTags, feedTagEntity)
                        .leftJoin(feedTagEntity.tag, tagEntity)
                        .where(builder)
                        .orderBy(orderSpecifier)
                        .groupBy(feedEntity.id, tagEntity.id)
                        .fetchOne())
                .orElse(0L);

        return new PageImpl<>(groupedFeedsDto, pageable, feedReadResCount);
    }
}
