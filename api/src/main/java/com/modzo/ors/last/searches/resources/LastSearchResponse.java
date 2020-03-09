package com.modzo.ors.last.searches.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.last.searches.domain.SearchedQuery;

import java.time.ZonedDateTime;

public class LastSearchResponse {

    private final String id;

    private final String query;

    private final String type;

    private final ZonedDateTime date;

    @JsonCreator
    private LastSearchResponse(@JsonProperty("id") String id,
                               @JsonProperty("query") String query,
                               @JsonProperty("type") String type,
                               @JsonProperty("date") ZonedDateTime date) {
        this.id = id;
        this.query = query;
        this.type = type;
        this.date = date;
    }

    static LastSearchResponse create(SearchedQuery searchedQuery) {
        return new LastSearchResponse(
                searchedQuery.getId(),
                searchedQuery.getQuery(),
                searchedQuery.getType().getTitle(),
                searchedQuery.getCreated()
        );
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

    public ZonedDateTime getDate() {
        return date;
    }
}
