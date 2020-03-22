package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrls;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

public class GetRadioStationStreamUrls {

    private final long streamId;

    private final Pageable pageable;

    public GetRadioStationStreamUrls(long streamId, Pageable pageable) {
        this.streamId = streamId;
        this.pageable = requireNonNull(pageable);
    }

    @Component
    public static class Handler {

        private final StreamUrls streamUrls;

        private final Validator validator;

        public Handler(StreamUrls streamUrls, Validator validator) {
            this.streamUrls = streamUrls;
            this.validator = validator;
        }

        @Transactional(readOnly = true)
        public Page<StreamUrl> handle(GetRadioStationStreamUrls command) {
            validator.validate(command);
            return streamUrls.findAllByStream_id(command.streamId, command.pageable);
        }
    }

    @Component
    private static class Validator {

        private final RadioStationStreams streams;

        public Validator(RadioStationStreams streams) {
            this.streams = streams;
        }

        void validate(GetRadioStationStreamUrls command) {
            if (command.streamId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_STEAM_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station stream id cannot be less or equal to zero");
            }

            if (streams.findById(command.streamId).isEmpty()) {
                throw new DomainException("FIELD_RADIO_STATION_STREAM_ID_IS_INCORRECT",
                        "Radio station stream with id is not available");
            }
        }
    }
}
