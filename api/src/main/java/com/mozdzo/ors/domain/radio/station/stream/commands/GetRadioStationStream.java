package com.mozdzo.ors.domain.radio.station.stream.commands;

import com.mozdzo.ors.domain.DomainException;
import com.mozdzo.ors.domain.radio.station.RadioStation;
import com.mozdzo.ors.domain.radio.station.RadioStations;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStreams;
import org.springframework.stereotype.Component;

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

        public RadioStationStream handle(GetRadioStationStream command) {
            validator.validate(command);
            return radioStationStreams.findByRadioStationIdAndId(command.radioStationId, command.streamId)
                    .orElseThrow(() -> new DomainException(
                            "RADIO_STATION_STREAM_BY_ID_NOT_FOUND",
                            "Radio station stream by id was not found")
                    );
        }
    }

    @Component
    private static class Validator {
        void validate(GetRadioStationStream command) {
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station id cannot be less or equal to zero");
            }
            if (command.streamId <= 0) {
                throw new DomainException("FIELD_STREAM_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station stream id cannot be less or equal to zero");
            }
        }
    }
}
