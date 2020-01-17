package com.modzo.ors.domain.radio.station.commands;

import com.modzo.ors.domain.DomainException;
import com.modzo.ors.domain.events.RadioStationUpdated;
import com.modzo.ors.domain.radio.station.RadioStation;
import com.modzo.ors.domain.radio.station.RadioStations;
import com.modzo.ors.domain.radio.station.genre.Genre;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class UpdateRadioStation {
    private final long radioStationId;

    private final UpdateRadioStation.Data data;

    public UpdateRadioStation(long radioStationId, UpdateRadioStation.Data data) {
        this.radioStationId = radioStationId;
        this.data = data;
    }

    public long getRadioStationId() {
        return radioStationId;
    }

    public UpdateRadioStation.Data getData() {
        return data;
    }

    public static class Data {
        private final String title;

        private final String website;

        private final Set<Genre> genres;

        public Data(String title, String website, Set<Genre> genres) {
            this.title = title;
            this.website = website;
            this.genres = genres;
        }

        public String getTitle() {
            return title;
        }

        public String getWebsite() {
            return website;
        }

        public Set<Genre> getGenres() {
            return genres;
        }
    }

    @Component
    public static class Handler {
        private final RadioStations radioStations;

        private final Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        public Handler(RadioStations radioStations, Validator validator,
                       ApplicationEventPublisher applicationEventPublisher) {
            this.radioStations = radioStations;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @Transactional
        public void handle(UpdateRadioStation command) {
            validator.validate(command);
            RadioStation radioStation = radioStations.findById(command.radioStationId).get();

            radioStation.setTitle(command.data.title);
            radioStation.setWebsite(command.data.website);
            radioStation.getGenres().addAll(command.data.genres);
            applicationEventPublisher.publishEvent(new RadioStationUpdated(radioStation, new RadioStationUpdated.Data(
                    radioStation.getUniqueId(),
                    radioStation.getTitle(),
                    radioStation.getWebsite(),
                    radioStation.getGenres().stream()
                            .map(genre -> new RadioStationUpdated.Data.Genre(genre.getUniqueId(), genre
                                    .getTitle()))
                            .collect(toSet())
            )));
        }
    }

    @Component
    private static class Validator {
        private final RadioStations radioStations;

        public Validator(RadioStations radioStations) {
            this.radioStations = radioStations;
        }

        void validate(UpdateRadioStation command) {
            if (!radioStations.findById(command.radioStationId).isPresent()) {
                throw new DomainException("FIELD_RADIO_STATION_HAS_INCORRECT_DATA",
                        format("Radio station id `%s` was not found", command.radioStationId));
            }
            if (isBlank(command.data.title)) {
                throw new DomainException("FIELD_TITLE_NOT_BLANK", "Field title cannot be blank");
            }
        }
    }
}
