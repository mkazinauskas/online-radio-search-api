package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.events.domain.RadioStationStreamCreated;
import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStreams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CreateRadioStationStream {
    private final long radioStationId;

    private final String url;

    public CreateRadioStationStream(long radioStationId, String url) {
        this.radioStationId = radioStationId;
        this.url = StringUtils.trim(url);
    }

    public long getRadioStationId() {
        return radioStationId;
    }

    public String getUrl() {
        return url;
    }

    private RadioStationStream toRadioStationStream() {
        return new RadioStationStream(this.radioStationId, this.url);
    }

    @Component
    public static class Handler {
        private final RadioStationStreams radioStationStreams;

        private final Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        private final RadioStations radioStations;

        public Handler(RadioStationStreams radioStationStreams,
                       Validator validator,
                       ApplicationEventPublisher applicationEventPublisher,
                       RadioStations radioStations) {
            this.radioStationStreams = radioStationStreams;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
            this.radioStations = radioStations;
        }

        @Transactional
        public Result handle(CreateRadioStationStream command) {
            validator.validate(command);
            RadioStationStream savedStream = radioStationStreams.save(command.toRadioStationStream());

            RadioStation radioStation = radioStations.findById(savedStream.getRadioStationId()).get();

            applicationEventPublisher.publishEvent(
                    new RadioStationStreamCreated(savedStream,
                            new RadioStationStreamCreated.Data(
                                    savedStream.getId(),
                                    savedStream.getUniqueId(),
                                    savedStream.getCreated(),
                                    savedStream.getRadioStationId(),
                                    radioStation.getUniqueId(),
                                    savedStream.getUrl())));
            return new Result(savedStream.getId());
        }
    }

    @Component
    private static class Validator {

        private final RadioStations radioStations;

        private final RadioStationStreams radioStationStreams;

        public Validator(RadioStations radioStations, RadioStationStreams radioStationStreams) {
            this.radioStations = radioStations;
            this.radioStationStreams = radioStationStreams;
        }

        void validate(CreateRadioStationStream command) {
            if (command.radioStationId <= 0) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_NOT_POSITIVE",
                        "Field radio station id should be positive");
            }
            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException("FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "Radio station with id is not available");
            }
            if (isBlank(command.url)) {
                throw new DomainException("FIELD_URL_NOT_BLANK", "Field url cannot be blank");
            }

            Optional<RadioStationStream> existing = radioStationStreams.findByRadioStationIdAndUrl(
                    command.radioStationId,
                    command.url
            );

            if (existing.isPresent()) {
                throw new DomainException(
                        "FIELD_URL_IS_DUPLICATE_FOR_RADIO_STATION",
                        String.format(
                                "Field url = `%s` is duplicate for radio station with id = `%s`",
                                command.url,
                                command.radioStationId
                        )
                );
            }
        }
    }

    public static class Result {
        public final long id;

        Result(long id) {
            this.id = id;
        }
    }
}
