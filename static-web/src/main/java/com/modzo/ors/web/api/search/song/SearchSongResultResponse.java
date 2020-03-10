package com.modzo.ors.web.api.search.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchSongResultResponse {

    private final long id;

    private final String title;

    @JsonCreator
    private SearchSongResultResponse(@JsonProperty("id") long id,
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
