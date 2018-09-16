package com.mozdzo.ors.domain.radio.station.song.commands;

import com.mozdzo.ors.domain.DomainException;
import com.mozdzo.ors.domain.radio.station.RadioStations;
import com.mozdzo.ors.domain.radio.station.song.Song;
import com.mozdzo.ors.domain.radio.station.song.Songs;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CreateSong {
    private final long radioStationId;

    private final String title;

    private final LocalDateTime playingTime;

    public CreateSong(long radioStationId, String title, LocalDateTime playingTime) {
        this.radioStationId = radioStationId;
        this.title = title;
        this.playingTime = playingTime;
    }

    private Song toSong() {
        return new Song(radioStationId, title, playingTime);
    }

    @Component
    public static class Handler {
        private final Songs songs;

        private final Validator validator;

        public Handler(Songs songs, Validator validator) {
            this.songs = songs;
            this.validator = validator;
        }

        @Transactional
        public Result handle(CreateSong command) {
            validator.validate(command);
            Song saved = songs.save(command.toSong());
            return new Result(saved.getId());
        }
    }

    @Component
    private static class Validator {
        private final RadioStations radioStations;

        public Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(CreateSong command) {
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_NOT_POSITIVE",
                        "Field radio station id should be positive");
            }
            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "Radio station with id is not available");
            }
            if (isBlank(command.title)) {
                throw new DomainException("FIELD_TITLE_NOT_BLANK", "Field title cannot be blank");
            }
            if (command.playingTime == null) {
                throw new DomainException("FIELD_PLAYING_TIME_NOT_NULL", "Field playing time cannot be blank");
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
