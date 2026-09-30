package com.cloudbox.metering.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/api/v1/health")
    public ResponseEntity<HealthResponse> getHealthStatus() {
        return ResponseEntity.ok().body(healthService.current());
    }
}
