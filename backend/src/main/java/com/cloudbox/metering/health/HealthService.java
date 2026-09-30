package com.cloudbox.metering.health;


import org.springframework.stereotype.Service;

@Service
public class HealthService {

    private final HealthProperties properties;

    public HealthService(HealthProperties properties) {
               this.properties=properties;
    }

    public HealthResponse current(){
        return new HealthResponse(properties.service(), properties.version(), properties.status());
    }
}
