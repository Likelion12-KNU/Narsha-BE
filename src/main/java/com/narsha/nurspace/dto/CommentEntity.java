package com.narsha.nurspace.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentEntity {
    @Schema(description = "id")
    private long comment_id;

    @Schema(description = "댓글 내용")
    private String contents;

}

