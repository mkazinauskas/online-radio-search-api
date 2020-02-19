package com.modzo.ors.last.searches.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.ZonedDateTime;

@Document(indexName = "searched_queries", type = "searched_query")
public class SearchedQuery {
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("created")
    private ZonedDateTime date = ZonedDateTime.now();

    @JsonProperty("query")
    private String query;

    SearchedQuery() {
    }

    public SearchedQuery(String query) {
        this.query = query;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
