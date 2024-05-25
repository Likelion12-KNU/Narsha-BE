package com.fullhouse.matzip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardListDTO {
    @Schema(description = "제목")
    String title;

    @Schema(description = "좋아요 수")
    int likes;

    @Schema(description = "수정 시간")
    LocalDateTime editDt;
}

