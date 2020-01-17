package com.modzo.ors.domain.radio.station.stream.commands;

import com.modzo.ors.domain.DomainException;
import com.modzo.ors.domain.events.RadioStationStreamUpdated;
import com.modzo.ors.domain.radio.station.RadioStations;
import com.modzo.ors.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.domain.radio.station.stream.RadioStationStreams;
import org.springframework.context.ApplicationEventPublisher;
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
            RadioStationStream stream = radioStationStreams
                    .findByRadioStationIdAndId(command.radioStationId, command.streamId).get();

            stream.setBitRate(command.data.bitRate);
            stream.setType(command.data.type);
            stream.setUrl(command.data.url);

            String radioStationUniqueId = radioStations.getOne(command.radioStationId).getUniqueId();

            applicationEventPublisher.publishEvent(
                    new RadioStationStreamUpdated(stream,
                            new RadioStationStreamUpdated.Data(
                                    stream.getUniqueId(),
                                    radioStationUniqueId,
                                    command.data.url,
                                    command.data.bitRate,
                                    command.data.type.name()
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
