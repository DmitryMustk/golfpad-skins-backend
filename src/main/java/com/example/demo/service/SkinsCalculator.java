package com.example.demo.service;

import com.example.demo.model.HoleResult;
import com.example.demo.model.LastHolePolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SkinsCalculator {

    private final LastHolePolicy lastHolePolicy;

    public SkinsCalculator(@Value("${skins.last-hole-policy:DROP}") String lastHolePolicy) {
        this.lastHolePolicy = LastHolePolicy.valueOf(lastHolePolicy.toUpperCase());
    }

    public CalculationResult calculate(Map<String, List<Integer>> playersScores, int holesCount) {
        Map<String, Integer> playerSkins = new LinkedHashMap<>();
        playersScores.keySet().forEach(id -> playerSkins.put(id, 0));

        List<HoleResult> holeResults = new ArrayList<>();
        int bank = 1;

        for (int holeIndex = 0; holeIndex < holesCount; holeIndex++) {
            int holeNumber = holeIndex + 1;
            int bankBefore = bank;

            Map<String, Integer> scoresOnHole = new LinkedHashMap<>();
            for (Map.Entry<String, List<Integer>> entry : playersScores.entrySet()) {
                scoresOnHole.put(entry.getKey(), entry.getValue().get(holeIndex));
            }

            int minScore = scoresOnHole.values().stream()
                    .min(Integer::compareTo)
                    .orElseThrow();

            List<String> winners = scoresOnHole.entrySet().stream()
                    .filter(e -> e.getValue() == minScore)
                    .map(Map.Entry::getKey)
                    .toList();

            if (winners.size() == 1) {
                String winnerId = winners.getFirst();
                int finalBank = bank;
                playerSkins.computeIfPresent(winnerId, (k, v) -> v + finalBank);

                holeResults.add(new HoleResult(holeNumber, bankBefore, scoresOnHole, winners));
                bank = 1;
            } else {
                holeResults.add(new HoleResult(holeNumber, bankBefore, scoresOnHole, List.of()));
                bank++;
            }
        }

        int unclaimedBank = 0;
        if (bank > 1) {
            unclaimedBank = applyLastHolePolicy(playerSkins, bank);
        }

        return new CalculationResult(playerSkins, holeResults, unclaimedBank);
    }

    private int applyLastHolePolicy(Map<String, Integer> playerSkins, int bank) {
        switch (lastHolePolicy) {
            case SPLIT -> {
                List<String> top = topPlayers(playerSkins);
                int share = bank / top.size();
                for (String id : top) {
                    playerSkins.put(id, playerSkins.get(id) + share);
                }
                return 0;
            }
            case GIVE_TO_BEST -> {
                List<String> top = topPlayers(playerSkins);
                if (top.size() == 1) {
                    String best = top.getFirst();
                    playerSkins.put(best, playerSkins.get(best) + bank);
                } else {
                    int share = bank / top.size();
                    for (String id : top) {
                        playerSkins.put(id, playerSkins.get(id) + share);
                    }
                }
                return 0;
            }
            default -> {
                return bank;
            }
        }
    }

    private List<String> topPlayers(Map<String, Integer> playerSkins) {
        int max = playerSkins.values().stream().max(Integer::compareTo).orElse(0);
        return playerSkins.entrySet().stream()
                .filter(e -> e.getValue() == max)
                .map(Map.Entry::getKey)
                .toList();
    }

    public record CalculationResult(
            Map<String, Integer> playerSkins,
            List<HoleResult> holeResults,
            int unclaimedBank
    ) {}
}