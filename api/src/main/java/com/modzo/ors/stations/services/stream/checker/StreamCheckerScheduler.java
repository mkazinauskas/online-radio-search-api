package com.modzo.ors.stations.services.stream.checker;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "application.stream.checker.scheduler.enabled", havingValue = "true")
class StreamCheckerScheduler {

    private final StreamChecker streamChecker;

    public StreamCheckerScheduler(StreamChecker streamChecker) {
        this.streamChecker = streamChecker;
    }

    @Scheduled(fixedDelay = 1000L)
    void check() {
        streamChecker.check();
    }
}
