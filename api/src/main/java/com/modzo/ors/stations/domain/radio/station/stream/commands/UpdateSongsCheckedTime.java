package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.events.domain.RadioStationStreamSongsCheckedUpdated;
import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static java.util.Objects.isNull;

public class UpdateSongsCheckedTime {

    private final long radioStationId;

    private final long streamId;

    private final ZonedDateTime songsCheckedTime;

    public UpdateSongsCheckedTime(long radioStationId, long streamId, ZonedDateTime songsCheckedTime) {
        this.radioStationId = radioStationId;
        this.streamId = streamId;
        this.songsCheckedTime = songsCheckedTime;
    }

    public long getRadioStationId() {
        return radioStationId;
    }

    public long getStreamId() {
        return streamId;
    }

    public ZonedDateTime getSongsCheckedTime() {
        return songsCheckedTime;
    }

    @Component
    public static class Handler {

        private final RadioStationStreams radioStationStreams;

        private final UpdateSongsCheckedTime.Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        public Handler(RadioStationStreams radioStationStreams,
                       UpdateSongsCheckedTime.Validator validator,
                       ApplicationEventPublisher applicationEventPublisher) {
            this.radioStationStreams = radioStationStreams;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @Transactional
        public void handle(UpdateSongsCheckedTime command) {
            validator.validate(command);
            RadioStationStream stream = radioStationStreams
                    .findByRadioStation_IdAndId(command.radioStationId, command.streamId).get();

            stream.setSongsChecked(command.songsCheckedTime);

            applicationEventPublisher.publishEvent(
                    new RadioStationStreamSongsCheckedUpdated(
                            stream,
                            new RadioStationStreamSongsCheckedUpdated.Data(
                                    stream.getId(),
                                    stream.getUniqueId(),
                                    stream.getRadioStation().getId(),
                                    stream.getRadioStation().getUniqueId(),
                                    command.getSongsCheckedTime()
                            )
                    )
            );
        }
    }

    @Component
    private static class Validator {

        private final RadioStations radioStations;

        public Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(UpdateSongsCheckedTime command) {
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_NOT_POSITIVE",
                        "Field radio station id should be positive");
            }
            if (radioStations.findById(command.radioStationId).isEmpty()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "Radio station with id is not available");
            }
            if (isNull(command.songsCheckedTime)) {
                throw new DomainException(
                        "FIELD_SONGS_CHECKED_TIME_IS_NULL",
                        "Field songs checked time should not be null"
                );
            }
        }
    }
}
