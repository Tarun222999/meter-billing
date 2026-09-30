package com.cloudbox.metering.health;

public record HealthResponse(
        String service,
        String version,
        String status) {
}
