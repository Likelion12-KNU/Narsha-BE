package com.fullhouse.matzip.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    // 좋아요 수 추가
    public void addLikes(){ this.likes++; }

    // 좋아요 수 제거
    public void minusLikes(){ this.likes--; }

    // Board와 Comment의 관계 설정
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // Comment 추가 메소드
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setBoard(this);
    }

    // Comment 제거 메소드
    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setBoard(null);
    }

    // 위도
    @Setter
    private double latitude;

    // 경도
    @Setter
    private double longitude;

    // 가게명
    @Setter
    private String storeName = "";

    // 태그
    @Setter
    private String tag;
}
