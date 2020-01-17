package com.modzo.ors.resources.admin.radio.station.stream;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

public class CreateRadioStationStreamRequest {
    @NotBlank
    @URL
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
