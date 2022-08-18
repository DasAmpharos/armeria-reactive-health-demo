# armeria-reactive-health-demo

Sample project to assist with https://github.com/line/armeria/issues/4396

## Getting Started

1. Install [SDKMAN](https://sdkman.io/)

```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

2. Install JDK17

```bash
sdk install java 17.0.4-tem
```

3. Run Application

```bash
./gradlew bootRun -Parmeria 
```

*NOTE*: `bootRun` without `-Parmeria` will exclude `com.linecorp.armeria:armeria-spring-boot2-actuator-starter` and
use `org.springframework.boot:spring-boot-starter-actuator` instead.

4. Get application health

```bash
curl http://localhost:8080/actuator/health 
```

## Description

When the `com.linecorp.armeria:armeria-spring-boot2-actuator-starter` dependency is included, the result
from `/actuator/health` will always be `HTTP 200` with the following response body:

```json
{
  "scanAvailable": true
}
```

Log statements showing that `CustomHealthIndicator#doHealthCheck` was invoked will appear in app logs, however the log
statements within the `Mono<Health>` returned from `CustomHealthIndicator#doHealthCheck` do not. This indicates that
the `Mono<Health>` returned from `CustomHealthIndicator#doHealthCheck` never received a subscription and the custom
health check logic was not invoked.

When excluding `com.linecorp.armeria:armeria-spring-boot2-actuator-starter`, the log statements within
the `Mono<Health>` returned from `CustomHealthIndicator#doHealthCheck` appear in the app logs and a random, simulated
health is returned from `/actuator/health`:

* When `r.nextBoolean() == true`:
    * Status Code:   `HTTP 200`
    * Response Body: `{"status": "UP"}`
* When `r.nextBoolean() == false`:
    * Status Code:   `HTTP 503`
    * Response Body: `{"status": "DOWN"}`