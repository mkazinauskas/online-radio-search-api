package com.modzo.ors.stations.services.stream.scrapper.songs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "application.songs.updater.scheduler.enabled", havingValue = "true")
class SongsUpdaterScheduler {

    private final SongsUpdater songsUpdater;

    SongsUpdaterScheduler(SongsUpdater songsUpdater) {
        this.songsUpdater = songsUpdater;
    }

    @Scheduled(fixedDelay = 1000L)
    void update() {
        songsUpdater.update();
    }
}
