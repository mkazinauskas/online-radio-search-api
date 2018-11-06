package com.mozdzo.ors.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "genres", type = "genre")
public class GenreDocument {
    @Id
    @JsonProperty("uniqueId")
    private String uniqueId;

    @JsonProperty("title")
    private String title;

    GenreDocument() {
    }

    public GenreDocument(String uniqueId, String title) {
        this.uniqueId = uniqueId;
        this.title = title;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}