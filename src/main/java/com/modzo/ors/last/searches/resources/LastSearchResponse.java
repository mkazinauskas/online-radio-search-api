package com.modzo.ors.last.searches.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.last.searches.domain.SearchedQuery;

import java.time.ZonedDateTime;

public class LastSearchResponse {

    private final Long id;

    private final String query;

    private final String type;

    private final ZonedDateTime created;

    @JsonCreator
    private LastSearchResponse(@JsonProperty("id") Long id,
                               @JsonProperty("query") String query,
                               @JsonProperty("type") String type,
                               @JsonProperty("created") ZonedDateTime created) {
        this.id = id;
        this.query = query;
        this.type = type;
        this.created = created;
    }

    static LastSearchResponse create(SearchedQuery searchedQuery) {
        return new LastSearchResponse(
                searchedQuery.getId(),
                searchedQuery.getQuery(),
                searchedQuery.getType().getTitle(),
                searchedQuery.getCreated()
        );
    }

    public Long getId() {
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
