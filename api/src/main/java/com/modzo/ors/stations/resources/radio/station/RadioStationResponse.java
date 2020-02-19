package com.modzo.ors.stations.resources.radio.station;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.RadioStation;

public class RadioStationResponse {
    private final long id;

    private final String title;

    private final String website;

    @JsonCreator
    private RadioStationResponse(@JsonProperty("id") long id,
                                 @JsonProperty("title") String title,
                                 @JsonProperty("website") String website) {
        this.id = id;
        this.title = title;
        this.website = website;
    }

    static RadioStationResponse create(RadioStation radioStation) {
        return new RadioStationResponse(radioStation.getId(), radioStation.getTitle(), radioStation.getWebsite());
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }
}
