package com.fullhouse.matzip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardListDTO {
    String title;
    int likes;
    LocalDateTime editDt;
}

