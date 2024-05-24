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

    private String title;
    private String contents;
    private int likes;
    private LocalDateTime writeDt;
    private LocalDateTime editDt;
}
