package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class FindRadioStationStreamByUrl {

    private final String url;

    public FindRadioStationStreamByUrl(String url) {
        this.url = url;
    }

    @Component
    public static class Handler {

        private final RadioStationStreams radioStationStreams;

        public Handler(RadioStationStreams radioStationStreams) {
            this.radioStationStreams = radioStationStreams;
        }

        @Transactional(readOnly = true)
        public Optional<RadioStationStream> handle(FindRadioStationStreamByUrl command) {
            return radioStationStreams.findByUrl(command.url);
        }
    }
}
