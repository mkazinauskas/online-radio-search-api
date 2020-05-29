package com.modzo.ors.stations.services.stream.checker;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.GetRadioStation;
import com.modzo.ors.stations.domain.radio.station.commands.UpdateRadioStation;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.commands.GetRadioStationStreams;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class RadioStationStatusUpdater {

    private final GetRadioStation.Handler getRadioStationHandler;

    private final GetRadioStationStreams.Handler getRadioStationStreamsHandler;

    private final UpdateRadioStation.Handler updateRadioStationHandler;

    RadioStationStatusUpdater(GetRadioStation.Handler getRadioStationHandler,
                              GetRadioStationStreams.Handler getRadioStationStreamsHandler,
                              UpdateRadioStation.Handler updateRadioStationHandler) {
        this.getRadioStationHandler = getRadioStationHandler;
        this.getRadioStationStreamsHandler = getRadioStationStreamsHandler;
        this.updateRadioStationHandler = updateRadioStationHandler;
    }

    void update(RadioStationStream stream) {
        long radioStationId = stream.getRadioStationId();
        List<RadioStationStream> streams = getRadioStationStreamsHandler.handle(
                new GetRadioStationStreams(radioStationId, Pageable.unpaged())
        ).getContent();

        long workingStreamsCount = streams.stream().filter(RadioStationStream::isWorking).count();
        if (workingStreamsCount == 0) {
            RadioStation radioStation = getRadioStationHandler.handle(new GetRadioStation(radioStationId));
            if (radioStation.isEnabled()) {
                updateRadioStationHandler.handle(new UpdateRadioStation(
                                radioStationId, new UpdateRadioStation.DataBuilder()
                                .fromCurrent(radioStation)
                                .setEnabled(false)
                                .build()
                        )
                );
            }
        }
    }
}
