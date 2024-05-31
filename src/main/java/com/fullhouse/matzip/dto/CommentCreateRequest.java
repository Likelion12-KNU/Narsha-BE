package com.fullhouse.matzip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCreateRequest {
    @Schema(description = "id")
    private long comment_id;

    public Long getComment_id(){
        return comment_id;

    @Schema(description = "댓글 내용")
    private String content;
    }

}

