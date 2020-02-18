package com.modzo.ors.domain.radio.station.song.commands;

import com.modzo.ors.domain.DomainException;
import com.modzo.ors.domain.events.RadioStationSongDeleted;
import com.modzo.ors.domain.radio.station.RadioStation;
import com.modzo.ors.domain.radio.station.RadioStations;
import com.modzo.ors.domain.radio.station.song.RadioStationSong;
import com.modzo.ors.domain.radio.station.song.RadioStationSongs;
import com.modzo.ors.domain.song.Songs;
import org.springframework.context.ApplicationEventPublisher;
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

        private final ApplicationEventPublisher applicationEventPublisher;

        private final RadioStations radioStations;

        public Handler(RadioStationSongs radioStationSongs,
                       Validator validator,
                       ApplicationEventPublisher applicationEventPublisher,
                       RadioStations radioStations) {
            this.radioStationSongs = radioStationSongs;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
            this.radioStations = radioStations;
        }

        @Transactional
        public void handle(DeleteRadioStationSong command) {
            validator.validate(command);

            RadioStationSong radioStationSong = radioStationSongs.findByRadioStationIdAndId(
                    command.radioStationId,
                    command.songId
            ).get();

            RadioStation radioStation = radioStations.getOne(radioStationSong.getRadioStationId());

            radioStationSongs.delete(radioStationSong);

            applicationEventPublisher.publishEvent(
                    new RadioStationSongDeleted(
                            radioStationSong,
                            new RadioStationSongDeleted.Data(
                                    radioStationSong.getUniqueId(),
                                    radioStation.getUniqueId()
                            ))
            );
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
                throw new DomainException("FIELD_RADIO_STATION_ID_INCORRECT", "Radio station by id was not found");
            }
            if (radioStationSongs.findById(command.songId).isEmpty()) {
                throw new DomainException("FIELD_SONG_ID_INCORRECT", "Radio station song by id was not found");
            }
        }
    }
}
