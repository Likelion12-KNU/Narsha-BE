package com.fullhouse.matzip.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 제목
    @Setter
    private String title = "";

    // 내용
    @Setter
    private String contents = "";

    // 좋아요 수
    private int likes = 0;

    // 생성 시간
    private LocalDateTime createDt = LocalDateTime.now();

    // 수정 시간
    private LocalDateTime editDt = LocalDateTime.now();

    // 엔티티가 수정될 때 자동으로 갱신
    @PreUpdate
    protected void onUpdate() {
        this.editDt = LocalDateTime.now();
    }

    public void addLikes(){ this.likes++; }

    public void minusLikes(){ this.likes--; }


}
