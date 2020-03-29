package com.modzo.ors.stations.services.stream.scrapper.info;

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrls;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
class InfoUpdater {

    private final InfoUpdaterService infoUpdaterService;

    private final StreamUrls streamUrls;

    InfoUpdater(InfoUpdaterService infoUpdaterService,
                StreamUrls streamUrls) {
        this.infoUpdaterService = infoUpdaterService;
        this.streamUrls = streamUrls;
    }

    void update() {
        ZonedDateTime before = ZonedDateTime.now().minus(5, ChronoUnit.DAYS);

        Optional<StreamUrl> stream = streamUrls
                .findTop1ByTypeAndCheckedBeforeOrCheckedIsNull(StreamUrl.Type.INFO, before);

        stream.ifPresent(it -> infoUpdaterService.update(it.getStream().getRadioStationId(), it.getId()));
    }
}