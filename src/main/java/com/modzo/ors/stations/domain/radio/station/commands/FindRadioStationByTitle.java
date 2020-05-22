package com.modzo.ors.stations.domain.radio.station.commands;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class FindRadioStationByTitle {

    private final String title;

    public FindRadioStationByTitle(String title) {
        this.title = title;
    }

    @Component
    public static class Handler {

        private final RadioStations radioStations;

        public Handler(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        public Optional<RadioStation> handle(FindRadioStationByTitle command) {
            return radioStations.findByTitle(command.title);
        }
    }
}
