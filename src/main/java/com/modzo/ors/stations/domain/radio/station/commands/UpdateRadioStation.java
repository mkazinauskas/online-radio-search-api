package com.modzo.ors.stations.domain.radio.station.commands;

import com.modzo.ors.events.domain.RadioStationUpdated;
import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import com.modzo.ors.stations.domain.radio.station.genre.Genres;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.nimbusds.oauth2.sdk.util.CollectionUtils.isNotEmpty;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
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

        public static class Genre {

            private final long id;

            public Genre(long id) {
                this.id = id;
            }

            public long getId() {
                return id;
            }
        }

        private final String title;

        private final String website;

        private final boolean enabled;

        private final Set<Data.Genre> genres = new HashSet<>();

        private Data(String title,
                     String website,
                     boolean enabled,
                     Set<Data.Genre> genres) {
            this.title = title;
            this.website = website;
            this.enabled = enabled;
            if (isNotEmpty(genres)) {
                this.genres.addAll(genres);
            }
        }

        public String getTitle() {
            return title;
        }

        public String getWebsite() {
            return website;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public Set<Data.Genre> getGenres() {
            return genres;
        }

        public Set<Long> getGenreIds() {
            return genres.stream()
                    .map(Data.Genre::getId)
                    .collect(Collectors.toSet());
        }
    }

    public static class DataBuilder {

        private String title;

        private String website;

        private boolean enabled;

        private Set<Data.Genre> genres;

        public DataBuilder fromCurrent(RadioStation radioStation) {
            setTitle(radioStation.getTitle());
            setWebsite(radioStation.getWebsite());
            setEnabled(radioStation.isEnabled());

            Set<UpdateRadioStation.Data.Genre> genres = radioStation.getGenres().stream()
                    .map(genre -> new UpdateRadioStation.Data.Genre(genre.getId()))
                    .collect(Collectors.toSet());
            this.setGenres(genres);

            return this;
        }

        public DataBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public DataBuilder setWebsite(String website) {
            this.website = website;
            return this;
        }

        public DataBuilder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public DataBuilder setGenres(Set<Data.Genre> genres) {
            this.genres = genres;
            return this;
        }

        public UpdateRadioStation.Data build() {
            return new UpdateRadioStation.Data(title, website, enabled, genres);
        }
    }

    @Component
    public static class Handler {

        private final RadioStations radioStations;

        private final Genres genres;

        private final Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        public Handler(RadioStations radioStations,
                       Genres genres,
                       Validator validator,
                       ApplicationEventPublisher applicationEventPublisher) {
            this.radioStations = radioStations;
            this.genres = genres;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @Transactional
        public void handle(UpdateRadioStation command) {
            validator.validate(command);
            RadioStation radioStation = radioStations.findById(command.radioStationId).get();

            radioStation.setTitle(command.data.title);
            radioStation.setWebsite(command.data.website);
            radioStation.setEnabled(command.data.enabled);

            List<Genre> foundGenres = genres.findAllById(command.data.getGenreIds());
            radioStation.getGenres().addAll(foundGenres);

            RadioStationUpdated.Data eventData = new RadioStationUpdated.Data(
                    radioStation.getId(),
                    radioStation.getUniqueId(),
                    radioStation.getTitle(),
                    radioStation.getWebsite(),
                    radioStation.isEnabled(),
                    radioStation.getGenres().stream()
                            .map(genre -> new RadioStationUpdated.Data.Genre(
                                            genre.getId(),
                                            genre.getUniqueId(),
                                            genre.getTitle()
                                    )
                            )
                            .collect(toSet())
            );

            applicationEventPublisher.publishEvent(
                    new RadioStationUpdated(radioStation, eventData)
            );
        }
    }

    @Component
    private static class Validator {

        private final RadioStations radioStations;

        private final Genres genres;

        public Validator(RadioStations radioStations, Genres genres) {
            this.radioStations = radioStations;
            this.genres = genres;
        }

        void validate(UpdateRadioStation command) {
            if (radioStations.findById(command.radioStationId).isEmpty()) {
                throw new DomainException("FIELD_RADIO_STATION_HAS_INCORRECT_DATA",
                        "radioStationId",
                        format("Radio station id `%s` was not found", command.radioStationId)
                );
            }
            if (isBlank(command.data.title)) {
                throw new DomainException("FIELD_TITLE_NOT_BLANK", "title", "Field title cannot be blank");
            }

            if (radioStations.findByTitle(command.data.title).isPresent()) {
                throw new DomainException(
                        "FIELD_TITLE_EXISTS",
                        "title",
                        String.format("Title `%s` already exists", command.data.title)
                );
            }

            List<Long> genreIds = command.getData().getGenres()
                    .stream()
                    .map(Data.Genre::getId)
                    .collect(toList());

            genreIds.forEach(id ->
                    this.genres.findById(id)
                            .orElseThrow(() -> genreWasNotFound(id))
            );
        }

        private DomainException genreWasNotFound(long genreId) {
            return new DomainException(
                    "GENRE_WAS_NOT_FOUND",
                    "genres",
                    format("Genre by id `%s` was not found", genreId)
            );
        }
    }
}
