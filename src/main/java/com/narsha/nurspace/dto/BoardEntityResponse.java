package com.narsha.nurspace.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class BoardEntityResponse {
    @Schema(description = "계시판 id")
    private long id;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String contents;

    @Schema(description = "좋아요 수")
    private int likes;

    @Schema(description = "수정 시간")
    private LocalDateTime editDt;

    @Schema(description = "CommentEntity 리스트")
    private List<CommentEntity> commentLists;

    @Schema(description = "좌표")
    private Coordinate coordinate;

    @Schema(description = "가게명")
    private String storeName;
}

