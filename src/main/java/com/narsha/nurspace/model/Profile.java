package com.narsha.nurspace.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long profile_id;

    // 이름
    @Setter
    private String name = "";

    // 간병 서비스 이력
    @Setter
    private String contents = "";

    // 좋아요 수
    private int likes = 0;

    // 좋아요 수 추가
    public void addLikes(){ this.likes++; }

    // 좋아요 수 제거
    public void minusLikes(){ this.likes--; }

}
