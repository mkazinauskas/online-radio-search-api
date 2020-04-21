package com.modzo.ors.search.resources.genre;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.search.domain.GenreDocument;

public class SearchGenreResultResponse {

    private final long id;

    private final String uniqueId;

    private final String title;

    @JsonCreator
    private SearchGenreResultResponse(@JsonProperty("id") long id,
                                      @JsonProperty("uniqueId") String uniqueId,
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

    public String getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return title;
    }
}
