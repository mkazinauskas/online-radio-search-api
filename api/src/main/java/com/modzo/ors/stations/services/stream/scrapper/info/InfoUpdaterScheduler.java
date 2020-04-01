package com.modzo.ors.stations.services.stream.scrapper.info;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "application.info.updater.scheduler.enabled", havingValue = "true")
class InfoUpdaterScheduler {

    private final InfoUpdater infoUpdater;

    InfoUpdaterScheduler(InfoUpdater infoUpdater) {
        this.infoUpdater = infoUpdater;
    }

    @Scheduled(fixedDelay = 1000L)
    void update() {
        infoUpdater.update();
    }
}
