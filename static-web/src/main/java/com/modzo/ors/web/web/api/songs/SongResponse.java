package com.modzo.ors.web.web.api.songs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SongResponse {

    private final long id;

    private final String title;

    @JsonCreator
    private SongResponse(@JsonProperty("id") long id,
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
