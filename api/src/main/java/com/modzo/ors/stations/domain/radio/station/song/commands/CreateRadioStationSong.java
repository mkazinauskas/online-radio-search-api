package com.modzo.ors.stations.domain.radio.station.song.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.events.domain.RadioStationSongCreated;
import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSongs;
import com.modzo.ors.stations.domain.song.Song;
import com.modzo.ors.stations.domain.song.Songs;
import org.springframework.context.ApplicationEventPublisher;
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

    public long getSongId() {
        return songId;
    }

    public long getRadioStationId() {
        return radioStationId;
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

        private final ApplicationEventPublisher applicationEventPublisher;

        private final Songs songs;

        private final RadioStations radioStations;

        public Handler(RadioStationSongs radioStationSongs,
                       Validator validator,
                       ApplicationEventPublisher applicationEventPublisher,
                       Songs songs,
                       RadioStations radioStations) {
            this.radioStationSongs = radioStationSongs;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
            this.songs = songs;
            this.radioStations = radioStations;
        }

        @Transactional
        public Result handle(CreateRadioStationSong command) {
            validator.validate(command);
            RadioStationSong radioStationSong = radioStationSongs.save(command.toRadioStationSong());

            RadioStation radioStation = radioStations.getOne(radioStationSong.getRadioStationId());

            Song song = songs.findById(command.songId).get();
            applicationEventPublisher.publishEvent(
                    new RadioStationSongCreated(
                            radioStationSong,
                            new RadioStationSongCreated.Data(
                                    radioStationSong.getUniqueId(),
                                    radioStation.getUniqueId(),
                                    song.getUniqueId(),
                                    radioStationSong.getPlayedTime()
                            ))
            );
            return new Result(radioStationSong.getId());
        }
    }

    @Component
    private static class Validator {
        private final RadioStations radioStations;

        private final Songs songs;

        public Validator(RadioStations radioStations, Songs songs) {
            this.radioStations = radioStations;
            this.songs = songs;
        }

        void validate(CreateRadioStationSong command) {
            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_INCORRECT", "Radio station by id was not found");
            }
            if (!songs.findById(command.songId).isPresent()) {
                throw new DomainException("FIELD_SONG_ID_INCORRECT", "Song by id was not found");
            }
            if (command.playedTime == null) {
                throw new DomainException("FIELD_PLAYED_TIME_IS_NULL", "Field played time cannot be blank");
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
