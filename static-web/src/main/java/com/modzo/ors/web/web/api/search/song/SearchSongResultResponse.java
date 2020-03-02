package com.modzo.ors.web.web.api.search.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchSongResultResponse {

    private final String uniqueId;

    private final String title;

    @JsonCreator
    private SearchSongResultResponse(@JsonProperty("uniqueId") String uniqueId,
                                     @JsonProperty("title") String title) {
        this.uniqueId = uniqueId;
        this.title = title;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return title;
    }
}
