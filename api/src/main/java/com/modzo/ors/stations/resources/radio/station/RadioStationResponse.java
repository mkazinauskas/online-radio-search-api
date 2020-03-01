package com.modzo.ors.stations.resources.radio.station;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.RadioStation;

class RadioStationResponse {

    private final long id;

    private final String uniqueId;

    private final String title;

    private final String website;

    @JsonCreator
    private RadioStationResponse(@JsonProperty("id") long id,
                                 @JsonProperty("uniqueId") String uniqueId,
                                 @JsonProperty("title") String title,
                                 @JsonProperty("website") String website) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
        this.website = website;
    }

    static RadioStationResponse create(RadioStation radioStation) {
        return new RadioStationResponse(
                radioStation.getId(),
                radioStation.getUniqueId(),
                radioStation.getTitle(),
                radioStation.getWebsite()
        );
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
