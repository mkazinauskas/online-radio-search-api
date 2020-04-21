package com.modzo.ors.stations.domain.radio.station.commands;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

public class GetRadioStationByGenreId {

    private final long genreId;

    private final Pageable pageable;

    public GetRadioStationByGenreId(long genreId, Pageable pageable) {
        this.genreId = genreId;
        this.pageable = requireNonNull(pageable);
    }

    @Component
    public static class Handler {

        private final RadioStations radioStations;

        public Handler(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        public Page<RadioStation> handle(GetRadioStationByGenreId command) {
            return radioStations.findAllByGenres_Id(command.genreId, command.pageable);
        }
    }
}
