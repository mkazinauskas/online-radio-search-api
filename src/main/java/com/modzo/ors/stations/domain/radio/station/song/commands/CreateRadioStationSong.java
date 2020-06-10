package com.modzo.ors.stations.domain.radio.station.song.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSongs;
import com.modzo.ors.stations.domain.song.Songs;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

public class CreateRadioStationSong {

    private final long songId;

    private final long radioStationId;

    private final ZonedDateTime playedTime;

    public CreateRadioStationSong(long songId, long radioStationId, ZonedDateTime playedTime) {
        this.songId = songId;
        this.radioStationId = radioStationId;
        this.playedTime = playedTime;
    }

    public ZonedDateTime getPlayedTime() {
        return playedTime;
    }

    private RadioStationSong toRadioStationSong() {
        return new RadioStationSong(radioStationId, songId, playedTime);
    }

    @Component
    public static class Handler {

        private final RadioStationSongs radioStationSongs;

        private final Validator validator;

        public Handler(RadioStationSongs radioStationSongs,
                       Validator validator) {
            this.radioStationSongs = radioStationSongs;
            this.validator = validator;
        }

        @Transactional
        public Result handle(CreateRadioStationSong command) {
            validator.validate(command);

            RadioStationSong radioStationSong = radioStationSongs.save(command.toRadioStationSong());

            return new Result(radioStationSong.getId());
        }
    }

    @Component
    private static class Validator {

        private final RadioStations radioStations;

        private final Songs songs;

        Validator(RadioStations radioStations, Songs songs) {
            this.radioStations = radioStations;
            this.songs = songs;
        }

        void validate(CreateRadioStationSong command) {
            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_INCORRECT",
                        "radioStationId",
                        "Radio station by id was not found"
                );
            }
            if (songs.findById(command.songId).isEmpty()) {
                throw new DomainException(
                        "FIELD_SONG_ID_INCORRECT",
                        "songId",
                        "Song by id was not found"
                );
            }
            if (command.playedTime == null) {
                throw new DomainException("FIELD_PLAYED_TIME_IS_NULL",
                        "playedTime",
                        "Field played time cannot be blank"
                );
            }
        }
    }

    public static class Result {
        public final long id;

        Result(long id) {
            this.id = id;
        }
    }
}
