package com.example.demo.model;

import java.util.List;
import java.util.Map;

public record HoleResult(
        int holeNumber,
        int bankBefore,
        Map<String, Integer> scores,
        List<String> winners
) {
}
