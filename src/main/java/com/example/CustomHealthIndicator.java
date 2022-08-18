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
        return Mono.fromSupplier(() -> {
            // simulate some process for health check
            var i = r.nextInt(0, 3);
            log.info("Simulating health check...");
            log.info(" > r.nextInt(0, 3)={}", i);
            var health = switch (i) {
                case 0 -> builder.up();
                case 1 -> builder.down();
                default -> builder.unknown();
            };
            return health.build();
        });
    }
}
