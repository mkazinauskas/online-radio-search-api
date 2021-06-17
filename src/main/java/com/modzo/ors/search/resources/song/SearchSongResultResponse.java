package com.modzo.ors.search.resources.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.song.Song;

import java.util.UUID;

public class SearchSongResultResponse {

    private final long id;

    private final UUID uniqueId;

    private final String title;

    @JsonCreator
    private SearchSongResultResponse(@JsonProperty("id") long id,
                                     @JsonProperty("uniqueId") UUID uniqueId,
                                     @JsonProperty("title") String title) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
    }

    static SearchSongResultResponse create(Song song) {
        return new SearchSongResultResponse(
                song.getId(),
                song.getUniqueId(),
                song.getTitle()
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
