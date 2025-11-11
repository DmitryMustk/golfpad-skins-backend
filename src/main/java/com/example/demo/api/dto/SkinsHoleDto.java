package com.example.demo.api.dto;

import java.util.List;
import java.util.Map;

public record SkinsHoleDto(
        int holeNumber,
        int bankBefore,
        Map<String, Integer> scores,
        List<String> winners
) {
}
