package com.fullhouse.matzip.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardListRequestDTO {
    @Schema(description = "BoardListDTO 리스트")
    List<BoardListDTO> boardLists;

    @Schema(description = "화면에 표기할 페이지 수")
    int viewCount;
}
