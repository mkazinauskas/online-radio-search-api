package com.modzo.ors.stations.services.stream.checker;

import com.modzo.ors.stations.domain.radio.station.stream.commands.FindOldestCheckedRadioStationStream;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Component
class StreamChecker {

    private final StreamCheckService streamCheckService;

    private final FindOldestCheckedRadioStationStream.Handler findOlderCheckerRadioStationStreamHandler;

    StreamChecker(StreamCheckService streamCheckService,
                  FindOldestCheckedRadioStationStream.Handler findOlderCheckerRadioStationStreamHandler) {
        this.streamCheckService = streamCheckService;
        this.findOlderCheckerRadioStationStreamHandler = findOlderCheckerRadioStationStreamHandler;
    }

    void update() {
        ZonedDateTime before = ZonedDateTime.now().minus(5, ChronoUnit.DAYS);

        findOlderCheckerRadioStationStreamHandler.handle(new FindOldestCheckedRadioStationStream(before))
                .ifPresent(it -> streamCheckService.checkIfStreamWorks(it.getRadioStationId(), it.getId()));
    }
}