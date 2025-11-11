package com.example.demo.service;

import com.example.demo.api.dto.SkinsPlayerDto;
import com.example.demo.api.dto.SkinsRequest;
import com.example.demo.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkinsRequestValidator {
    public void validate(SkinsRequest request) {
        List<SkinsPlayerDto> players = request.players();
        if (players == null || players.isEmpty()) {
            throw new ApiException("at least one player must be provided", HttpStatus.BAD_REQUEST);
        }

        players.forEach(p -> {
            if (p.scores().size() != request.holesCount()) {
                throw new ApiException("all players must have the same number of hole scores", HttpStatus.BAD_REQUEST);
            }
        });
    }
}
