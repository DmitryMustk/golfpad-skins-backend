package com.example.demo.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SkinsRequest(
        @NotEmpty(message = "at least one player must be provided")
        List<@Valid SkinsPlayerDto> players,

        @Min(value = 1, message = "at least one hole must be provided")
        Integer holesCount
) {
}
