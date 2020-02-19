package com.modzo.ors.stations.resources.radio.station.stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;

public class RadioStationStreamResponse {

    private final long id;

    private final String url;

    @JsonCreator
    private RadioStationStreamResponse(@JsonProperty("id") long id, @JsonProperty("url") String url) {
        this.id = id;
        this.url = url;
    }

    static RadioStationStreamResponse create(RadioStationStream radioStationStream) {
        return new RadioStationStreamResponse(radioStationStream.getId(), radioStationStream.getUrl());
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
