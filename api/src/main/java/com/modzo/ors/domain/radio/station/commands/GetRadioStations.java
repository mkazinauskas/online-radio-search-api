package com.modzo.ors.domain.radio.station.commands;

import com.modzo.ors.domain.radio.station.RadioStation;
import com.modzo.ors.domain.radio.station.RadioStations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

public class GetRadioStations {
    private final Pageable pageable;

    public GetRadioStations(Pageable pageable) {
        this.pageable = requireNonNull(pageable);
    }

    @Component
    public static class Handler {
        private final RadioStations radioStations;

        public Handler(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        public Page<RadioStation> handle(GetRadioStations command) {
            return radioStations.findAll(command.pageable);
        }
    }
}
