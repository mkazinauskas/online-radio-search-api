package com.modzo.ors.search.resources.radio.station;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.search.domain.GenreDocument;
import com.modzo.ors.search.domain.RadioStationDocument;

import java.util.List;
import java.util.stream.Collectors;

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

    static SearchRadioStationResultResponse create(RadioStationDocument radioStationDocument) {
        return new SearchRadioStationResultResponse(
                radioStationDocument.getId(),
                radioStationDocument.getUniqueId(),
                radioStationDocument.getTitle(),
                radioStationDocument.getWebsite(),
                radioStationDocument.getGenres().stream()
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

        private static GenreResponse create(GenreDocument genreDocument) {
            return new GenreResponse(
                    genreDocument.getId(),
                    genreDocument.getUniqueId(),
                    genreDocument.getTitle()
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
