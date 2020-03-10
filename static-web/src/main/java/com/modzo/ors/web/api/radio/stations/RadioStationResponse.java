package com.modzo.ors.web.api.radio.stations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RadioStationResponse {

    private final long id;

    private final String uniqueId;

    private final String title;

    private final String website;

    private final List<GenreResponse> genres;

    @JsonCreator
    public RadioStationResponse(@JsonProperty("id") long id,
                                @JsonProperty("uniqueId") String uniqueId,
                                @JsonProperty("title") String title,
                                @JsonProperty("website") String website,
                                @JsonProperty("genres") List<GenreResponse> genres) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
        this.website = website;
        this.genres = genres;
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

        public GenreResponse(
                @JsonProperty("id") long id,
                @JsonProperty("uniqueId") String uniqueId,
                @JsonProperty("title") String title) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.title = title;
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
