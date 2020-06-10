package com.modzo.ors.stations.domain.radio.station.song.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSongs;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

public class DeleteRadioStationSong {

    private final long radioStationId;

    private final long songId;

    public DeleteRadioStationSong(long radioStationId, long songId) {
        this.radioStationId = radioStationId;
        this.songId = songId;
    }

    public long getRadioStationId() {
        return radioStationId;
    }

    public long getSongId() {
        return songId;
    }

    @Component
    public static class Handler {
        private final RadioStationSongs radioStationSongs;

        private final Validator validator;

        public Handler(RadioStationSongs radioStationSongs,
                       Validator validator
        ) {
            this.radioStationSongs = radioStationSongs;
            this.validator = validator;
        }

        @Transactional
        public void handle(DeleteRadioStationSong command) {
            validator.validate(command);

            RadioStationSong radioStationSong = radioStationSongs.findByRadioStationIdAndId(
                    command.radioStationId,
                    command.songId
            ).get();

            radioStationSongs.delete(radioStationSong);
        }
    }

    @Component
    private static class Validator {

        private final RadioStations radioStations;

        private final RadioStationSongs radioStationSongs;

        public Validator(RadioStations radioStations, RadioStationSongs radioStationSongs) {
            this.radioStations = radioStations;
            this.radioStationSongs = radioStationSongs;
        }

        void validate(DeleteRadioStationSong command) {
            if (radioStations.findById(command.radioStationId).isEmpty()) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_INCORRECT",
                        "radioStationId",
                        "Radio station by id was not found"
                );
            }
            if (radioStationSongs.findById(command.songId).isEmpty()) {
                throw new DomainException(
                        "FIELD_SONG_ID_INCORRECT",
                        "songId",
                        "Radio station song by id was not found"
                );
            }
        }
    }
}
