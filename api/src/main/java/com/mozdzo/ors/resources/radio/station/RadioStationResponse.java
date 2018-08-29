package com.mozdzo.ors.resources.radio.station;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mozdzo.ors.domain.radio.station.RadioStation;

public class RadioStationResponse {
    private final long id;

    private final String title;

    @JsonCreator
    private RadioStationResponse(@JsonProperty("id") long id, @JsonProperty("title") String title) {
        this.id = id;
        this.title = title;
    }

    static RadioStationResponse create(RadioStation radioStation) {
        return new RadioStationResponse(radioStation.getId(), radioStation.getTitle());
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
