package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrls;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

public class GetRadioStationStreamUrl {

    private final long radioStationId;

    private final long streamId;

    private final long urlId;

    public GetRadioStationStreamUrl(long radioStationId,
                                    long streamId,
                                    long urlId) {
        this.radioStationId = radioStationId;
        this.streamId = streamId;
        this.urlId = urlId;
    }

    @Component
    public static class Handler {

        private final StreamUrls urls;

        private final Validator validator;

        public Handler(StreamUrls urls, Validator validator) {
            this.urls = urls;
            this.validator = validator;
        }

        @Transactional(readOnly = true)
        public StreamUrl handle(GetRadioStationStreamUrl command) {
            validator.validate(command);

            return urls.findById(command.urlId).get();
        }
    }

    @Component
    private static class Validator {

        private final RadioStations radioStations;

        private final RadioStationStreams streams;

        private final StreamUrls urls;

        public Validator(RadioStations radioStations,
                         RadioStationStreams streams,
                         StreamUrls urls) {
            this.radioStations = radioStations;
            this.streams = streams;
            this.urls = urls;
        }

        void validate(GetRadioStationStreamUrl command) {
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station id cannot be less or equal to zero");
            }

            if (radioStations.findById(command.radioStationId).isEmpty()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "Radio station with id is not available");
            }

            if (command.streamId <= 0) {
                throw new DomainException("FIELD_STREAM_ID_IS_LESS_OR_EQUAL_TO_ZERO",
                        "Radio station stream id cannot be less or equal to zero");
            }

            if (streams.findById(command.streamId).isEmpty()) {
                throw new DomainException("FIELD_RADIO_STATION_STREAM_ID_IS_INCORRECT",
                        "Radio station stream with id is not available");
            }

            if (urls.findById(command.urlId).isEmpty()) {
                throw new DomainException("FIELD_RADIO_STATION_STREAM_URL_ID_IS_INCORRECT",
                        "Radio station stream url with id is not available");
            }
        }
    }
}
