package com.fullhouse.matzip.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    // 제목
    private String title;

    // 내용
    private String contents;

    // 좋아요 수
    private int likes;

    // 생성 시간
    private LocalDateTime writeDt;

    // 수정 시간
    private LocalDateTime editDt;
}
