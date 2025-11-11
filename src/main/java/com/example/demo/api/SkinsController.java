package com.example.demo.api;

import com.example.demo.api.dto.SkinsRequest;
import com.example.demo.api.dto.SkinsResponse;
import com.example.demo.service.SkinsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skins")
@CrossOrigin(origins = "*")
public class SkinsController {
    private final SkinsService skinsService;

    public SkinsController(SkinsService skinsService) {
        this.skinsService = skinsService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<SkinsResponse> calculate(
            @Valid @RequestBody SkinsRequest skinsRequest
    ) {
        return ResponseEntity.ok(skinsService.calculate(skinsRequest));
    }
}