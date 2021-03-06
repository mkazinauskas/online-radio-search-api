package com.modzo.ors.stations.resources.admin.radio.station.create;

import javax.validation.constraints.NotBlank;

public class CreateRadioStationRequest {
    @NotBlank
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
