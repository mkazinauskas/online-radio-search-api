package com.mozdzo.ors.resources.admin.radio.station.stream.update;

import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.commands.GetRadioStationStream;
import org.springframework.stereotype.Component;

@Component
public class LatestInfoService {
    private final GetRadioStationStream.Handler radioStationStream;

    public LatestInfoService(GetRadioStationStream.Handler radioStationStream) {
        this.radioStationStream = radioStationStream;
    }

    public void update(long radioStationId, long streamId) {
        RadioStationStream stream = radioStationStream.handle(new GetRadioStationStream(radioStationId, streamId));
    }
}
