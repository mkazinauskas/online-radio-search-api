package com.modzo.ors.stations.services.stream.scrapper.songs;

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.commands.FindOldestCheckedRadioStationStreamUrl;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
class SongsUpdater {

    private final FindOldestCheckedRadioStationStreamUrl.Handler findOldestCheckedRadioStationStreamUrlHandler;

    private final SongsUpdaterService updaterService;

    SongsUpdater(FindOldestCheckedRadioStationStreamUrl.Handler findOldestCheckedRadioStationStreamUrlHandler,
                         SongsUpdaterService updaterService) {
        this.findOldestCheckedRadioStationStreamUrlHandler = findOldestCheckedRadioStationStreamUrlHandler;
        this.updaterService = updaterService;
    }

    void update() {
        ZonedDateTime before = ZonedDateTime.now().minus(1, ChronoUnit.HOURS);

        findOldestCheckedRadioStationStreamUrlHandler.handle(
                new FindOldestCheckedRadioStationStreamUrl(StreamUrl.Type.SONGS, before)
        ).ifPresent(it -> updaterService.update(it.getStream().getRadioStationId(), it.getStream().getId()));
    }
}
