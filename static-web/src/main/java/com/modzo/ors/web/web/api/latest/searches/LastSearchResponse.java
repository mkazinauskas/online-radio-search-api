package com.modzo.ors.web.web.api.latest.searches;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class LastSearchResponse {

    private final String id;

    private final String query;

    private final String type;

    private final ZonedDateTime created;

    @JsonCreator
    private LastSearchResponse(@JsonProperty("id") String id,
                               @JsonProperty("query") String query,
                               @JsonProperty("type") String type,
                               @JsonProperty("created") ZonedDateTime created) {
        this.id = id;
        this.query = query;
        this.type = type;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public String getQuery() {
        return query;
    }

    public String getType() {
        return type;
    }

    public ZonedDateTime getCreated() {
        return created;
    }
}
