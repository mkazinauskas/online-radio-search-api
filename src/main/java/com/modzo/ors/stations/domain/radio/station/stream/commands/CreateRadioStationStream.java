package com.modzo.ors.stations.domain.radio.station.stream.commands;

import com.modzo.ors.commons.Urls;
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
import java.util.Set;

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

    @Component
    public static class Handler {

        private final RadioStations stations;

        private final RadioStationStreams radioStationStreams;

        private final Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        private final RadioStations radioStations;

        public Handler(RadioStations stations, RadioStationStreams radioStationStreams,
                       Validator validator,
                       ApplicationEventPublisher applicationEventPublisher,
                       RadioStations radioStations) {
            this.stations = stations;
            this.radioStationStreams = radioStationStreams;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
            this.radioStations = radioStations;
        }

        @Transactional
        public Result handle(CreateRadioStationStream command) {
            validator.validate(command);

            RadioStation station = stations.findById(command.radioStationId).get();

            RadioStationStream stream = new RadioStationStream(station, command.url);

            RadioStationStream savedStream = radioStationStreams.save(stream);

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
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_IS_NOT_POSITIVE",
                        "radioStationId",
                        "Field radio station id should be positive"
                );
            }

            if (radioStations.findById(command.radioStationId).isEmpty()) {
                throw new DomainException(
                        "FIELD_RADIO_STATION_ID_IS_INCORRECT",
                        "radioStationId",
                        "Radio station with id is not available"
                );
            }

            if (Urls.isNotValid(command.url)) {
                throw new DomainException(
                        "FIELD_URL_NOT_VALID",
                        "url",
                        "Field url is not valid"
                );
            }

            if (StringUtils.length(command.url) > 100) {
                throw new DomainException(
                        "FIELD_URL_TOO_LONG",
                        "url",
                        "Field url cannot be longer than 100 characters"
                );
            }

            Optional<RadioStationStream> existing = radioStationStreams.findByRadioStation_IdAndUrl(
                    command.radioStationId,
                    command.url
            );

            if (existing.isPresent()) {
                throw new DomainException(
                        "FIELD_URL_IS_DUPLICATE_FOR_RADIO_STATION",
                        Set.of("url", "radioStationId"),
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
