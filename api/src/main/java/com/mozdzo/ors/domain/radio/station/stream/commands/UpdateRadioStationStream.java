package com.mozdzo.ors.domain.radio.station.stream.commands;

import com.mozdzo.ors.domain.DomainException;
import com.mozdzo.ors.domain.radio.station.RadioStations;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStreams;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class UpdateRadioStationStream {

    private final long radioStationId;

    private final long streamId;

    private final Data data;

    public UpdateRadioStationStream(long radioStationId, long streamId, Data data) {
        this.radioStationId = radioStationId;
        this.streamId = streamId;
        this.data = data;
    }

    public long getRadioStationId() {
        return radioStationId;
    }

    public long getStreamId() {
        return streamId;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private final String url;
        private final Integer bitRate;
        private final RadioStationStream.Type type;

        public Data(String url, Integer bitRate, RadioStationStream.Type type) {
            this.url = url;
            this.bitRate = bitRate;
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public Integer getBitRate() {
            return bitRate;
        }

        public RadioStationStream.Type getType() {
            return type;
        }
    }

    @Component
    public static class Handler {
        private final RadioStationStreams radioStationStreams;

        private final Validator validator;

        Handler(RadioStationStreams radioStationStreams, Validator validator) {
            this.radioStationStreams = radioStationStreams;
            this.validator = validator;
        }

        @Transactional
        public void handle(UpdateRadioStationStream command) {
            validator.validate(command);
            RadioStationStream radioStation = radioStationStreams
                    .findByRadioStationIdAndId(command.radioStationId, command.streamId).get();

            radioStation.setBitRate(command.data.bitRate);
            radioStation.setType(command.data.type);
            radioStation.setUrl(command.data.url);
        }
    }

    @Component
    private static class Validator {

        private final RadioStations radioStations;

        public Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(UpdateRadioStationStream command) {
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_NOT_POSITIVE",
                        "Field radio station id should be positive");
            }
            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "Radio station with id is not available");
            }
            if (isBlank(command.data.url)) {
                throw new DomainException("FIELD_URL_NOT_BLANK", "Field data url cannot be blank");
            }
        }
    }
}
