package com.cloudbox.metering.health;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "metering.health")
public record HealthProperties(
        String service,
        String version,
        String status) {
}