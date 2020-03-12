package com.modzo.ors.stations.services.stream.scrapper.songs;

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
class SongsUpdater {

    private final RadioStationStreams radioStationStreams;

    private final SongsUpdaterService updaterService;

    private SongsUpdater(RadioStationStreams radioStationStreams,
                         SongsUpdaterService updaterService) {
        this.radioStationStreams = radioStationStreams;
        this.updaterService = updaterService;
    }

    void update() {
        ZonedDateTime checkingTime = ZonedDateTime.now();

        ZonedDateTime before = ZonedDateTime.now().minus(1, ChronoUnit.HOURS);

        Optional<RadioStationStream> stream = radioStationStreams.findOneBySongsCheckedIsBeforeOrSongsCheckedIsNullOrderBySongsCheckedAsc(before);

        stream.ifPresent(it ->
                {
                    it.setSongsChecked(checkingTime);
                    radioStationStreams.save(it);
                    updaterService.update(it.getRadioStationId(), it.getId());
                }
        );
    }
}
