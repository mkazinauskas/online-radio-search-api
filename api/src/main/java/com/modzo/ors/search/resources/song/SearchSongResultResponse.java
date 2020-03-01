package com.modzo.ors.search.resources.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.search.domain.SongDocument;

public class SearchSongResultResponse {

    private final String uniqueId;

    private final String title;

    @JsonCreator
    private SearchSongResultResponse(@JsonProperty("uniqueId") String uniqueId,
                                     @JsonProperty("title") String title) {
        this.uniqueId = uniqueId;
        this.title = title;
    }

    static SearchSongResultResponse create(SongDocument song) {
        return new SearchSongResultResponse(song.getUniqueId(), song.getTitle());
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return title;
    }
}
