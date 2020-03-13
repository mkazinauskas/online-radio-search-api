package com.modzo.ors.stations.services.stream.scrapper.stream;

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
class InfoUpdater {

    private final InfoUpdaterService infoUpdaterService;

    private final RadioStationStreams radioStationStreams;

    InfoUpdater(InfoUpdaterService infoUpdaterService,
                RadioStationStreams radioStationStreams) {
        this.infoUpdaterService = infoUpdaterService;
        this.radioStationStreams = radioStationStreams;
    }

    void update() {
        ZonedDateTime before = ZonedDateTime.now().minus(5, ChronoUnit.DAYS);

        Optional<RadioStationStream> stream = radioStationStreams
                .findOneByInfoCheckedIsBeforeOrInfoCheckedIsNullOrderByInfoCheckedAsc(before);

        stream.ifPresent(it -> infoUpdaterService.update(it.getRadioStationId(), it.getId()));
    }

}
