package com.mozdzo.ors.searches.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mozdzo.ors.searches.domain.SearchedQuery;

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

    static LastSearchResponse create(SearchedQuery searchedQuery) {
        return new LastSearchResponse(
                searchedQuery.getId(),
                searchedQuery.getQuery(),
                searchedQuery.getDate()
        );
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
