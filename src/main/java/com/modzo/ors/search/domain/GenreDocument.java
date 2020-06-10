package com.modzo.ors.search.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

@Document(indexName = "online_radio_search_genres")
public class GenreDocument {

    @Id
    @JsonProperty("id")
    private long id;

    @JsonProperty("uniqueId")
    private UUID uniqueId;

    @JsonProperty("title")
    private String title;

    GenreDocument() {
    }

    public GenreDocument(long id, UUID uniqueId, String title) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
