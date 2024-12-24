package com.sparta.team7instagram.domain.tag.repository;

import com.sparta.team7instagram.domain.tag.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
    Optional<TagEntity> findByName(String name);
}
