package com.modzo.ors.stations.resources.genre;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import com.modzo.ors.stations.domain.song.Song;

class GenreResponse {

    private final long id;

    private final String title;

    @JsonCreator
    private GenreResponse(@JsonProperty("id") long id,
                          @JsonProperty("title") String title) {
        this.id = id;
        this.title = title;
    }

    static GenreResponse create(Genre genre) {
        return new GenreResponse(genre.getId(), genre.getTitle());
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
