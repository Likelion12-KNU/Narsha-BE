package com.narsha.nurspace.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Coordinate {
    @Schema(description = "위도")
    private double latitude;

    @Schema(description = "경도")
    private double longitude;

}
