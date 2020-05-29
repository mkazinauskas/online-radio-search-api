package com.modzo.ors.stations.domain.radio.station.commands;

import com.modzo.ors.events.domain.RadioStationCreated;
import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CreateRadioStation {

    private final String title;

    public CreateRadioStation(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private RadioStation toRadioStation() {
        return new RadioStation(this.title);
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
                    new RadioStationCreated(
                            radioStation,
                            new RadioStationCreated.Data(
                                    radioStation.getId(),
                                    radioStation.getUniqueId(),
                                    radioStation.getCreated(),
                                    radioStation.getTitle(),
                                    radioStation.isEnabled()
                            )
                    )
            );
            return new Result(radioStation.getId());
        }
    }

    @Component
    private static class Validator {
        void validate(CreateRadioStation command) {
            if (isBlank(command.title)) {
                throw new DomainException("FIELD_TITLE_NOT_BLANK", "Field title cannot be blank");
            }
            if (StringUtils.length(command.title) > 100) {
                throw new DomainException("FIELD_TITLE_TOO_LONG", "Field title cannot be longer than 100 characters");
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
