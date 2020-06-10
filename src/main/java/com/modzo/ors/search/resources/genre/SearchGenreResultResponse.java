package com.modzo.ors.search.resources.genre;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.search.domain.GenreDocument;

import java.util.UUID;

public class SearchGenreResultResponse {

    private final long id;

    private final UUID uniqueId;

    private final String title;

    @JsonCreator
    private SearchGenreResultResponse(@JsonProperty("id") long id,
                                      @JsonProperty("uniqueId") UUID uniqueId,
                                      @JsonProperty("title") String title) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
    }

    static SearchGenreResultResponse create(GenreDocument genreDocument) {
        return new SearchGenreResultResponse(
                genreDocument.getId(),
                genreDocument.getUniqueId(),
                genreDocument.getTitle()
        );
    }

    public long getId() {
        return id;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return title;
    }
}
