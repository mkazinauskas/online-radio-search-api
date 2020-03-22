package com.modzo.ors.stations.resources.admin.radio.station.stream.urls;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;

import javax.validation.constraints.NotNull;

class ResolveUrlRequest {

    @NotNull
    private final StreamUrl.Type type;

    @JsonCreator
    public ResolveUrlRequest(@JsonProperty("type") StreamUrl.Type type) {
        this.type = type;
    }

    public StreamUrl.Type getType() {
        return type;
    }
}
