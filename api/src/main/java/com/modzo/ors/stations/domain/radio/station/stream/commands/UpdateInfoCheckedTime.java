package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.events.domain.RadioStationStreamInfoCheckedUpdated;
import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static java.util.Objects.isNull;

public class UpdateInfoCheckedTime {

    private final long radioStationId;

    private final long streamId;

    private final ZonedDateTime infoCheckedTime;

    public UpdateInfoCheckedTime(long radioStationId, long streamId, ZonedDateTime infoCheckedTime) {
        this.radioStationId = radioStationId;
        this.streamId = streamId;
        this.infoCheckedTime = infoCheckedTime;
    }

    public long getRadioStationId() {
        return radioStationId;
    }

    public long getStreamId() {
        return streamId;
    }

    public ZonedDateTime getInfoCheckedTime() {
        return infoCheckedTime;
    }

    @Component
    public static class Handler {

        private final RadioStationStreams radioStationStreams;

        private final UpdateInfoCheckedTime.Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        public Handler(RadioStationStreams radioStationStreams,
                       UpdateInfoCheckedTime.Validator validator,
                       ApplicationEventPublisher applicationEventPublisher) {
            this.radioStationStreams = radioStationStreams;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @Transactional
        public void handle(UpdateInfoCheckedTime command) {
            validator.validate(command);
            RadioStationStream stream = radioStationStreams
                    .findByRadioStationIdAndId(command.radioStationId, command.streamId).get();

            stream.setSongsChecked(command.infoCheckedTime);

            applicationEventPublisher.publishEvent(
                    new RadioStationStreamInfoCheckedUpdated(
                            stream,
                            new RadioStationStreamInfoCheckedUpdated.Data(
                                    stream.getId(),
                                    stream.getUniqueId(),
                                    stream.getInfoChecked()
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

        void validate(UpdateInfoCheckedTime command) {
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_NOT_POSITIVE",
                        "Field radio station id should be positive");
            }
            if (radioStations.findById(command.radioStationId).isEmpty()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "Radio station with id is not available");
            }
            if (isNull(command.infoCheckedTime)) {
                throw new DomainException(
                        "FIELD_INFO_CHECKED_TIME_IS_NULL",
                        "Field info checked time should not be null"
                );
            }
        }
    }
}
