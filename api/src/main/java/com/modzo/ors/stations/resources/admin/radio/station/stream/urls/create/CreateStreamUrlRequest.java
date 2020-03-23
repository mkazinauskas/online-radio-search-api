package com.modzo.ors.stations.resources.admin.radio.station.stream.urls.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

class CreateStreamUrlRequest {

    @NotNull
    private StreamUrl.Type type;

    @NotBlank
    @URL
    private String url;

    @JsonCreator
    CreateStreamUrlRequest(@JsonProperty("type") StreamUrl.Type type,
                           @JsonProperty("url") String url) {
        this.type = type;
        this.url = url;
    }

    public StreamUrl.Type getType() {
        return type;
    }

    public void setType(StreamUrl.Type type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
