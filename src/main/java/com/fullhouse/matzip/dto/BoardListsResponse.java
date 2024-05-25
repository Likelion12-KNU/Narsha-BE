package com.fullhouse.matzip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardListsResponse {
    @Schema(description = "BoardListDTO 리스트")
    private List<BoardEntityResponse> boardLists;

    @Schema(description = "화면에 표기할 페이지 수", defaultValue = "10", example = "30")
    private int viewCount;
}
