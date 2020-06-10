package com.modzo.ors.stations.domain.radio.station.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.events.StationsDomainEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CreateRadioStation {

    private final UUID uniqueId;

    private final String title;

    public CreateRadioStation(String title) {
        this.uniqueId = null;
        this.title = title;
    }

    public CreateRadioStation(UUID uniqueId, String title) {
        this.uniqueId = uniqueId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private RadioStation toRadioStation() {
        if (Objects.isNull(uniqueId)) {
            return new RadioStation(this.title);
        } else {
            return new RadioStation(this.uniqueId, this.title);
        }
    }

    @Component
    public static class Handler {

        private final RadioStations radioStations;

        private final Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        Handler(RadioStations radioStations,
                Validator validator,
                ApplicationEventPublisher applicationEventPublisher) {
            this.radioStations = radioStations;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @Transactional
        public Result handle(CreateRadioStation command) {
            validator.validate(command);
            RadioStation radioStation = radioStations.save(command.toRadioStation());
            applicationEventPublisher.publishEvent(
                    new StationsDomainEvent(
                            radioStation,
                            StationsDomainEvent.Action.REFRESHED,
                            StationsDomainEvent.Type.RADIO_STATION,
                            radioStation.getId()
                    )
            );
            return new Result(radioStation.getId());
        }

    }

    @Component
    private static class Validator {

        private final RadioStations radioStations;

        public Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(CreateRadioStation command) {
            if (isBlank(command.title)) {
                throw new DomainException("FIELD_TITLE_NOT_BLANK", "title", "Field title cannot be blank");
            }
            if (StringUtils.length(command.title) > 100) {
                throw new DomainException(
                        "FIELD_TITLE_TOO_LONG",
                        "title",
                        "Field title cannot be longer than 100 characters"
                );
            }

            if (radioStations.findByTitle(command.title).isPresent()) {
                throw new DomainException(
                        "FIELD_TITLE_EXISTS",
                        "title",
                        String.format("Radio station with title `%s` already exists", command.title)
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
