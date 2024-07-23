package com.narsha.nurspace.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileUpdateRequest {
    @Schema(description = "이름")
    private String name;

    @Schema(description = "간병 서비스 이력")
    private String contents;

}