package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Random;

@Slf4j
@Component
public class CustomHealthIndicator extends AbstractReactiveHealthIndicator {
    private final Random r = new Random();

    @Override
    protected Mono<Health> doHealthCheck(Health.Builder builder) {
        log.info("CustomHealthIndicator#doHealthCheck");
        return Mono.fromSupplier(() -> {
            // simulate some process for health check
            var isUp = r.nextBoolean();
            log.info(" > Simulating health check...");
            log.info(" > Health is {}", isUp ? "up" : "down");
            var health = isUp ? builder.up() : builder.down();
            return health.build();
        });
    }
}
