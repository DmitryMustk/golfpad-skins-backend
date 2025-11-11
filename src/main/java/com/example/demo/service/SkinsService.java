package com.example.demo.service;

import com.example.demo.api.dto.SkinsPlayerDto;
import com.example.demo.api.dto.SkinsRequest;
import com.example.demo.api.dto.SkinsResponse;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SkinsService {

    private final SkinsRequestValidator validator;
    private final SkinsCalculator calculator;
    private final SkinsResponseMapper responseMapper;

    public SkinsService(
            SkinsRequestValidator validator,
            SkinsCalculator calculator,
            SkinsResponseMapper responseMapper
    ) {
        this.validator = validator;
        this.calculator = calculator;
        this.responseMapper = responseMapper;
    }

    public SkinsResponse calculate(SkinsRequest request) {
        validator.validate(request);

        Map<String, List<Integer>> scoresByPlayer = new LinkedHashMap<>();
        for (SkinsPlayerDto p : request.players()) {
            scoresByPlayer.put(p.id(), p.scores());
        }

        SkinsCalculator.CalculationResult calc = calculator.calculate(scoresByPlayer, request.holesCount());

        return responseMapper.toResponse(calc);
    }
}
