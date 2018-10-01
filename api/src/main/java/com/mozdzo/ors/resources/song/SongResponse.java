package com.mozdzo.ors.resources.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mozdzo.ors.domain.song.Song;

import java.time.ZonedDateTime;

public class SongResponse {

    private final long id;

    private final String title;

    @JsonCreator
    private SongResponse(@JsonProperty("id") long id,
                         @JsonProperty("title") String title) {
        this.id = id;
        this.title = title;
    }

    static SongResponse create(Song song) {
        return new SongResponse(song.getId(), song.getTitle());
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
