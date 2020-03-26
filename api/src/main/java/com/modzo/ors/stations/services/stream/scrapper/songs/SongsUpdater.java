package com.modzo.ors.stations.services.stream.scrapper.songs;

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrls;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
class SongsUpdater {

    private final StreamUrls streamUrls;

    private final SongsUpdaterService updaterService;

    private SongsUpdater(StreamUrls streamUrls,
                         SongsUpdaterService updaterService) {
        this.streamUrls = streamUrls;
        this.updaterService = updaterService;
    }

    void update() {
        ZonedDateTime before = ZonedDateTime.now().minus(1, ChronoUnit.HOURS);

        Optional<StreamUrl> stream = streamUrls
                .findTop1ByTypeAndCheckedBeforeOrCheckedIsNull(StreamUrl.Type.SONGS, before);

        stream.ifPresent(it -> updaterService.update(it.getStream().getRadioStationId(), it.getId()));
    }
}
