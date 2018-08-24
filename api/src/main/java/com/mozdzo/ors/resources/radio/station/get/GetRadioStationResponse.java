package com.mozdzo.ors.resources.radio.station.get;

import com.mozdzo.ors.domain.station.RadioStation;

public class GetRadioStationResponse {
    private long id;
    private String title;

    public GetRadioStationResponse() {
    }

    public GetRadioStationResponse(RadioStation radioStation) {
        this.id = radioStation.getId();
        this.title = radioStation.getTitle();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
