package com.example.demo.service;

import com.example.demo.api.dto.SkinsPlayerDto;
import com.example.demo.api.dto.SkinsRequest;
import com.example.demo.api.dto.SkinsResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkinsServiceTest {

    @Test
    void calculate_integrationLike() {
        SkinsRequestValidator validator = new SkinsRequestValidator();
        SkinsCalculator calculator = new SkinsCalculator("DROP");
        SkinsResponseMapper mapper = new SkinsResponseMapper();

        SkinsService service = new SkinsService(validator, calculator, mapper);

        SkinsRequest request = new SkinsRequest(
                List.of(
                        new SkinsPlayerDto("p1", List.of(4, 4, 5)),
                        new SkinsPlayerDto("p2", List.of(5, 4, 6))
                ),
                3
        );

        SkinsResponse response = service.calculate(request);

        assertNotNull(response);
        assertEquals(2, response.playerSkins().size());

        assertEquals(3, response.playerSkins().get("p1"));
        assertEquals(0, response.playerSkins().get("p2"));
        assertEquals("p1", response.winner());
        assertEquals(3, response.holes().size());
    }
}