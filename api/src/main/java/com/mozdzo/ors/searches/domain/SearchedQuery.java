package com.mozdzo.ors.searches.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.ZonedDateTime;
import java.util.UUID;

@Document(indexName = "searched_queries", type = "searched_query")
public class SearchedQuery {
    @Id
    @JsonProperty("uniqueId")
    private String uniqueId = UUID.randomUUID().toString();

    @JsonProperty("created")
    private ZonedDateTime date = ZonedDateTime.now();

    @JsonProperty("query")
    private String query;

    SearchedQuery() {
    }

    public SearchedQuery(String query) {
        this.query = query;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
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
