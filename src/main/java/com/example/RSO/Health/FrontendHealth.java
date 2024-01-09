package com.example.RSO.Health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class FrontendHealth implements HealthIndicator {

    private final WebClient webClient;

    @Autowired
    public FrontendHealth(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://20.84.17.217/").build();
    }


    @Override
    public Health health() {
        try {
            // Send a GET request to the frontend URL
            webClient.head().uri("/").retrieve().toBodilessEntity().block();
            return Health.up().withDetail("message", "Frontend is reachable").build();
        } catch (Exception e) {
            return Health.down().withDetail("message", "Frontend is not reachable").build();
        }
    }
}
