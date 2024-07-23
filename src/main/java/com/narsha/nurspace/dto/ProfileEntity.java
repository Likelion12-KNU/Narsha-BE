package com.narsha.nurspace.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileEntity {
    @Schema(description = "프로필 id")
    private long id;

    @Schema(description = "유저 이름")
    private String name;

    @Schema(description = "간병 서비스 이력")
    private String contents;

    @Schema(description = "좋아요 수")
    private int likes;

}
