package com.cloudbox.metering.health;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class HealthServiceTest {
    @Test 
    void shouldReturnCurrentHealthStatus() {
        HealthService healthService = new HealthService(
            new HealthProperties("service-metering",
"0.0.1", "UP"));
    assertEquals(new HealthResponse("service-metering",
"0.0.1", "UP"),
            healthService.current());
    }
}
