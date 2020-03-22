package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class GetRadioStationStreamUrlByType {

    private final long radioStationId;

    private final long streamId;

    private final StreamUrl.Type type;

    public GetRadioStationStreamUrlByType(long radioStationId,
                                          long streamId,
                                          StreamUrl.Type type) {
        this.radioStationId = radioStationId;
        this.streamId = streamId;
        this.type = type;
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
        public Optional<StreamUrl> handle(GetRadioStationStreamUrlByType command) {
            validator.validate(command);

            var stream = radioStationStreams.findByIdAndRadioStation_Id(command.radioStationId, command.streamId);

            StreamUrl streamUrl = stream.get()
                    .getUrls()
                    .get(command.type);

            return Optional.ofNullable(streamUrl);
        }
    }

    @Component
    private static class Validator {
        private final RadioStations radioStations;

        public Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(GetRadioStationStreamUrlByType command) {
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station id cannot be less or equal to zero");
            }

            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "Radio station with id is not available");
            }

            if (command.streamId <= 0) {
                throw new DomainException("FIELD_STREAM_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station stream id cannot be less or equal to zero");
            }
        }
    }
}
