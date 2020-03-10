package com.modzo.ors.stations.resources.radio.station;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.RadioStation;
import com.modzo.ors.stations.domain.radio.station.genre.Genre;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

class RadioStationResponse {

    private final long id;

    private final String uniqueId;

    private final ZonedDateTime created;

    private final String title;

    private final String website;

    private final List<GenreResponse> genres;

    @JsonCreator
    private RadioStationResponse(@JsonProperty("id") long id,
                                 @JsonProperty("uniqueId") String uniqueId,
                                 @JsonProperty("created") ZonedDateTime created,
                                 @JsonProperty("title") String title,
                                 @JsonProperty("website") String website,
                                 @JsonProperty("genres") List<GenreResponse> genres) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.created = created;
        this.title = title;
        this.website = website;
        this.genres = genres;
    }

    static RadioStationResponse create(RadioStation radioStation) {
        return new RadioStationResponse(
                radioStation.getId(),
                radioStation.getUniqueId(),
                radioStation.getCreated(),
                radioStation.getTitle(),
                radioStation.getWebsite(),
                radioStation.getGenres().stream()
                        .map(GenreResponse::create)
                        .collect(Collectors.toList())
        );
    }

    public long getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public List<GenreResponse> getGenres() {
        return genres;
    }

    public static class GenreResponse {

        private final long id;

        private final String uniqueId;

        private final String title;

        public GenreResponse(long id, String uniqueId, String title) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.title = title;
        }

        private static GenreResponse create(Genre genre) {
            return new GenreResponse(
                    genre.getId(),
                    genre.getUniqueId(),
                    genre.getTitle()
            );
        }

        public long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public String getTitle() {
            return title;
        }
    }
}