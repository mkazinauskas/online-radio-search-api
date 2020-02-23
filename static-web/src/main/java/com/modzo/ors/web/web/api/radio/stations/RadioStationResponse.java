package com.modzo.ors.web.web.api.radio.stations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RadioStationResponse {

    private final long id;

    private final String uniqueId;

    private final String title;

    private final String website;

    @JsonCreator
    public RadioStationResponse(@JsonProperty("id") long id,
                                 @JsonProperty("uniqueId") String uniqueId,
                                 @JsonProperty("title") String title,
                                 @JsonProperty("website") String website) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
        this.website = website;
    }

    public long getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }
}
