package com.modzo.ors.last.searches.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.ZonedDateTime;

@Document(indexName = "online_radio_search_searched_queries")
public class SearchedQuery {

    public enum Type {
        SONG("song"), RADIO_STATION("radiostation"), GENRE("genre");

        private final String title;

        Type(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("query")
    private String query;

    @JsonProperty("type")
    private Type type;

    SearchedQuery() {
    }

    public SearchedQuery(String query, Type type) {
        this.query = query;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getCreated() {
        return null;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
