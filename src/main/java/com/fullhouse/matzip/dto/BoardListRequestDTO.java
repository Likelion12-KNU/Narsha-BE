package com.fullhouse.matzip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardListRequestDTO {
    List<BoardListDTO> boardLists;
    int viewCount;
}
