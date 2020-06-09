package com.modzo.ors.search.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "online_radio_search_radio_stations")
public class RadioStationDocument {

    @Id
    @JsonProperty("id")
    private long id;

    @JsonProperty("uniqueId")
    private String uniqueId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("website")
    private String website;

    @JsonProperty("enabled")
    private boolean enabled;

    RadioStationDocument() {
    }

    public RadioStationDocument(long id, String uniqueId, String title, boolean enabled) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
