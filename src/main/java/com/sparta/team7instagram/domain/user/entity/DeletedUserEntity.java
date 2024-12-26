package com.sparta.team7instagram.domain.user.entity;

import com.sparta.team7instagram.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class DeletedUserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    public DeletedUserEntity(String email){
        this.email = email;
    }
}
