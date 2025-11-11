package com.example.demo.service;

import com.example.demo.api.dto.SkinsPlayerDto;
import com.example.demo.api.dto.SkinsRequest;
import com.example.demo.exception.ApiException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SkinsRequestValidatorTest {
    private final SkinsRequestValidator validator = new SkinsRequestValidator();

    @Test
    void validate_ok_whenAllPlayersHaveSameHoles() {
        SkinsRequest request = new SkinsRequest(
                List.of(
                        new SkinsPlayerDto("p1", List.of(4, 5, 4)),
                        new SkinsPlayerDto("p2", List.of(5, 5, 4))
                ),
                3
        );
        assertDoesNotThrow(() -> validator.validate(request));
    }

    @Test
    void validate_throws_whenNoPlayers() {
        SkinsRequest request = new SkinsRequest(List.of(), null);
        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(request));
        assertTrue(ex.getMessage().contains("at least one player"));
    }

    @Test
    void validate_throws_whenDifferentHolesCount() {
        SkinsRequest request = new SkinsRequest(
                List.of(
                        new SkinsPlayerDto("p1", List.of(4, 5, 4)),
                        new SkinsPlayerDto("p2", List.of(5, 5))
                ),
                3
        );

        ApiException ex = assertThrows(ApiException.class, () -> validator.validate(request));
        assertTrue(ex.getMessage().contains("same number"));
    }

}
