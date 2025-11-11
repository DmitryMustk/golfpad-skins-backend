package com.example.demo.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SkinsPlayerDto(
        @NotBlank(message = "player id must not be blank")
        String id,

        @NotEmpty(message = "scores must not be empty")
        List<@Min(value = 1, message = "score must be >= 1") Integer> scores
) {
}
