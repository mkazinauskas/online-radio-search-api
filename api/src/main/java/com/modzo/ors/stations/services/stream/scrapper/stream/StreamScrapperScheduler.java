package com.modzo.ors.stations.services.stream.scrapper.stream;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "application.info.updater.scheduler.enabled", havingValue = "true")
class StreamScrapperScheduler {

    private final InfoUpdater infoUpdater;

    StreamScrapperScheduler(InfoUpdater infoUpdater) {
        this.infoUpdater = infoUpdater;
    }

    @Scheduled(fixedDelay = 1000L)
    void update() {
        infoUpdater.update();
    }
}
