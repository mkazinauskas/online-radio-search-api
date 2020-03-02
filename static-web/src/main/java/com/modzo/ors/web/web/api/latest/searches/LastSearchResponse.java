package com.modzo.ors.web.web.api.latest.searches;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class LastSearchResponse {

    private final String id;

    private final String query;

    private final ZonedDateTime date;

    @JsonCreator
    private LastSearchResponse(@JsonProperty("id") String id,
                               @JsonProperty("query") String query,
                               @JsonProperty("date") ZonedDateTime date) {
        this.id = id;
        this.query = query;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getQuery() {
        return query;
    }

    public ZonedDateTime getDate() {
        return date;
    }
}
