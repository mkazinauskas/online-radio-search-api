package com.modzo.ors.web.web.api.search.radio.station;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchRadioStationResultResponse {

    private final long id;

    private final String uniqueId;

    private final String title;

    private final String website;

    private final List<GenreResponse> genres;

    @JsonCreator
    private SearchRadioStationResultResponse(@JsonProperty("id") long id,
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

        public GenreResponse(long id, String uniqueId, String title) {
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
