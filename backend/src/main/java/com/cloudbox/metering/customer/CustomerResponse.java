package com.cloudbox.metering.customer;

import java.time.Instant;

public record CustomerResponse(
    Long id,
    String name,
    String email,
    Instant createdAt
) {
    
}
