package com.example.demo.api.dto;

import java.util.List;
import java.util.Map;

public record SkinsResponse(
        Map<String, Integer> playerSkins,
        String winner,
        List<SkinsHoleDto> holes,
        int unclaimedBank
) {
}
