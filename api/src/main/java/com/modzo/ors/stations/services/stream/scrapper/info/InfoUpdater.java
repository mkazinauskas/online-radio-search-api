package com.modzo.ors.stations.services.stream.scrapper.info;

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.commands.FindOldestCheckedRadioStationStreamUrl;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
class InfoUpdater {

    private final InfoUpdaterService infoUpdaterService;

    private final FindOldestCheckedRadioStationStreamUrl.Handler findOldestCheckedRadioStationStreamUrlHandler;

    InfoUpdater(InfoUpdaterService infoUpdaterService,
                FindOldestCheckedRadioStationStreamUrl.Handler findOldestCheckedRadioStationStreamUrlHandler) {
        this.infoUpdaterService = infoUpdaterService;
        this.findOldestCheckedRadioStationStreamUrlHandler = findOldestCheckedRadioStationStreamUrlHandler;
    }

    void update() {
        ZonedDateTime before = ZonedDateTime.now().minus(5, ChronoUnit.DAYS);

        findOldestCheckedRadioStationStreamUrlHandler.handle(
                new FindOldestCheckedRadioStationStreamUrl(StreamUrl.Type.INFO, before)
        ).ifPresent(it -> infoUpdaterService.update(it.getStream().getRadioStationId(), it.getId()));
    }
}