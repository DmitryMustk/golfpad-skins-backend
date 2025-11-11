package com.example.demo.service;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SkinsCalculatorTest {

    @Test
    void calculate_simpleCase_oneWinnerEachHole() {
        SkinsCalculator calculator = new SkinsCalculator("DROP");

        Map<String, List<Integer>> scores = new LinkedHashMap<>();
        scores.put("p1", List.of(4, 4, 4));
        scores.put("p2", List.of(5, 5, 5));

        var result = calculator.calculate(scores, 3);

        assertEquals(3, result.playerSkins().get("p1"));
        assertEquals(0, result.playerSkins().get("p2"));
        assertEquals(0, result.unclaimedBank());
        assertEquals(3, result.holeResults().size());
    }

    @Test
    void calculate_carriesBankOnTie() {
        SkinsCalculator calculator = new SkinsCalculator("DROP");

        Map<String, List<Integer>> scores = new LinkedHashMap<>();

        scores.put("p1", List.of(4, 3));
        scores.put("p2", List.of(4, 4));

        var result = calculator.calculate(scores, 2);

        assertEquals(2, result.playerSkins().get("p1"));
        assertEquals(0, result.playerSkins().get("p2"));
    }

    @Test
    void calculate_lastBankDrop() {
        SkinsCalculator calculator = new SkinsCalculator("DROP");

        Map<String, List<Integer>> scores = new LinkedHashMap<>();

        scores.put("p1", List.of(4));
        scores.put("p2", List.of(4));

        var result = calculator.calculate(scores, 1);

        assertEquals(0, result.playerSkins().get("p1"));
        assertEquals(0, result.playerSkins().get("p2"));
        assertEquals(2, result.unclaimedBank());
    }

    @Test
    void calculate_lastBankSplit() {
        SkinsCalculator calculator = new SkinsCalculator("SPLIT");

        Map<String, List<Integer>> scores = new LinkedHashMap<>();
        scores.put("p1", List.of(4));
        scores.put("p2", List.of(4));

        var result = calculator.calculate(scores, 1);

        assertEquals(1, result.playerSkins().get("p1"));
        assertEquals(1, result.playerSkins().get("p2"));
        assertEquals(0, result.unclaimedBank());
    }

}