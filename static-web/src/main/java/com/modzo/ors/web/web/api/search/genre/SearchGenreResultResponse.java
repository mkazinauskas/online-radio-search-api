package com.modzo.ors.web.web.api.search.genre;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchGenreResultResponse {

    private final long id;

    private final String title;

    @JsonCreator
    private SearchGenreResultResponse(@JsonProperty("id") long id,
                                      @JsonProperty("title") String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
