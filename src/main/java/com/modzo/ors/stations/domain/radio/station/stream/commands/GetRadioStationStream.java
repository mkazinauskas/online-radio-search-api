package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public class GetRadioStationStream {
    private final long radioStationId;

    private final long streamId;

    public GetRadioStationStream(long radioStationId, long streamId) {
        this.radioStationId = radioStationId;
        this.streamId = streamId;
    }

    @Component
    public static class Handler {
        private final RadioStationStreams radioStationStreams;

        private final Validator validator;

        public Handler(RadioStationStreams radioStationStreams, Validator validator) {
            this.radioStationStreams = radioStationStreams;
            this.validator = validator;
        }

        @Transactional(readOnly = true)
        public RadioStationStream handle(GetRadioStationStream command) {
            validator.validate(command);
            return radioStationStreams.findByRadioStation_IdAndId(command.radioStationId, command.streamId)
                    .orElseThrow(() -> new DomainException(
                                "RADIO_STATION_STREAM_BY_ID_NOT_FOUND",
                                Set.of("radioStationId", "streamId"),
                                "Radio station stream by id was not found"
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

        void validate(GetRadioStationStream command) {
            if (command.radioStationId <= 0) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "radioStationId",
                        "Radio station id cannot be less or equal to zero");
            }

            if (radioStations.findById(command.radioStationId).isEmpty()) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "radioStationId",
                        "Radio station with id is not available");
            }

            if (command.streamId <= 0) {
                throw new DomainException(
                        "FIELD_STREAM_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "streamId",
                        "Radio station stream id cannot be less or equal to zero"
                );
            }
        }
    }
}
