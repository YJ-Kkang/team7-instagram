package com.sparta.team7instagram.domain.tag.service;

import com.sparta.team7instagram.domain.tag.entity.TagEntity;
import com.sparta.team7instagram.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public TagEntity findTagByName(String name) {
        return tagRepository.findByName(name)
                .orElseGet(() -> tagRepository.save(TagEntity.builder()
                        .name(name)
                        .build()
                ));
    }
}
