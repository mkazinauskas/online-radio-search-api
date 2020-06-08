package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.events.domain.RadioStationStreamUpdated;
import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream.Type.UNKNOWN;
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

        private final boolean working;

        private Data(String url, Integer bitRate, RadioStationStream.Type type, boolean working) {
            this.url = url;
            this.bitRate = bitRate;
            this.type = type;
            this.working = working;
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

        public boolean isWorking() {
            return working;
        }
    }

    public static class DataBuilder {

        private String url;

        private Integer bitRate;

        private RadioStationStream.Type type;

        private boolean working;

        public DataBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public DataBuilder setBitRate(Integer bitRate) {
            this.bitRate = bitRate;
            return this;
        }

        public DataBuilder setType(RadioStationStream.Type type) {
            this.type = type;
            return this;
        }

        public DataBuilder setWorking(boolean working) {
            this.working = working;
            return this;
        }

        public UpdateRadioStationStream.Data build() {
            return new UpdateRadioStationStream.Data(url, bitRate, type, working);
        }
    }

    @Component
    public static class Handler {

        private final RadioStationStreams radioStationStreams;

        private final RadioStations radioStations;

        private final Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        public Handler(RadioStationStreams radioStationStreams,
                       RadioStations radioStations,
                       Validator validator,
                       ApplicationEventPublisher applicationEventPublisher) {
            this.radioStationStreams = radioStationStreams;
            this.radioStations = radioStations;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @Transactional
        public void handle(UpdateRadioStationStream command) {
            validator.validate(command);

            RadioStationStream.Type type = Optional.ofNullable(command.data.type)
                    .orElse(UNKNOWN);

            RadioStationStream stream = radioStationStreams
                    .findByRadioStation_IdAndId(command.radioStationId, command.streamId).get();

            stream.setBitRate(command.data.bitRate);
            stream.setType(type);
            stream.setUrl(command.data.url);
            stream.setWorking(command.data.working);
            String radioStationUniqueId = radioStations.getOne(command.radioStationId).getUniqueId();

            applicationEventPublisher.publishEvent(
                    new RadioStationStreamUpdated(stream,
                            new RadioStationStreamUpdated.Data(
                                    stream.getId(),
                                    stream.getUniqueId(),
                                    radioStationUniqueId,
                                    command.data.url,
                                    command.data.bitRate,
                                    type.name(),
                                    command.data.working
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

        void validate(UpdateRadioStationStream command) {
            if (command.radioStationId <= 0) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_IS_NOT_POSITIVE",
                        "radioStationId",
                        "Field radio station id should be positive");
            }
            if (radioStations.findById(command.radioStationId).isEmpty()) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "radioStationId",
                        "Radio station with id is not available"
                );
            }
            if (isBlank(command.data.url)) {
                throw new DomainException(
                        "FIELD_URL_NOT_BLANK",
                        "url",
                        "Field data url cannot be blank"
                );
            }
        }
    }
}
