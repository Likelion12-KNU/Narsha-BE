package com.narsha.nurspace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long comment_id;

    @Setter
    private String contents = "";

    // Comment와 Board의 관계 설정
    @Setter
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

}
