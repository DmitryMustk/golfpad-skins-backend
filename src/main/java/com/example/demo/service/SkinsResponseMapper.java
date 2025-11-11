package com.example.demo.service;

import com.example.demo.api.dto.SkinsHoleDto;
import com.example.demo.api.dto.SkinsResponse;
import com.example.demo.model.HoleResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SkinsResponseMapper {

    public SkinsResponse toResponse(SkinsCalculator.CalculationResult calc) {
        String winner = determineWinner(calc.playerSkins());

        List<SkinsHoleDto> holes = new ArrayList<>();
        for (HoleResult hr : calc.holeResults()) {
            holes.add(new SkinsHoleDto(
                    hr.holeNumber(),
                    hr.bankBefore(),
                    hr.scores(),
                    hr.winners()
            ));
        }

        return new SkinsResponse(
                calc.playerSkins(),
                winner,
                holes,
                calc.unclaimedBank()
        );
    }

    private String determineWinner(Map<String, Integer> playerSkins) {
        int max = playerSkins.values().stream().max(Integer::compareTo).orElse(0);
        List<String> best = new ArrayList<>();
        playerSkins.forEach((id, val) -> {
            if (val == max) {
                best.add(id);
            }
        });
        return best.size() == 1 ? best.getFirst() : "TIE";
    }
}