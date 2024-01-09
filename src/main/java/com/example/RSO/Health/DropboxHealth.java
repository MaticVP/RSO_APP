package com.example.RSO.Health;

import com.example.RSO.Service.Service.DropboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DropboxHealth implements HealthIndicator {

    @Autowired
    DropboxService dropboxService;


    @Override
    public Health health() {
        try {
            dropboxService.dropBoxPing();
            return Health.up().withDetail("message", "Dropbox API is reachable").build();
        } catch (Exception e) {
            return Health.down().withDetail("message", "Dropbox API is not reachable").build();
        }
    }

}
