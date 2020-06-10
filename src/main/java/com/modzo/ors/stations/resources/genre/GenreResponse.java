package com.modzo.ors.stations.resources.genre;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.genre.Genre;

import java.time.ZonedDateTime;
import java.util.UUID;

class GenreResponse {

    private final long id;

    private final UUID uniqueId;

    private final ZonedDateTime created;

    private final String title;

    @JsonCreator
    private GenreResponse(@JsonProperty("id") long id,
                          @JsonProperty("uniqueId") UUID uniqueId,
                          @JsonProperty("created") ZonedDateTime created,
                          @JsonProperty("title") String title) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.created = created;
        this.title = title;
    }

    static GenreResponse create(Genre genre) {
        return new GenreResponse(genre.getId(), genre.getUniqueId(), genre.getCreated(), genre.getTitle());
    }

    public long getId() {
        return id;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public String getTitle() {
        return title;
    }
}
