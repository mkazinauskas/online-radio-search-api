package com.modzo.ors.web.web.api.radio.stations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

public class RadioStationResource extends RepresentationModel {

    private final RadioStationResponse radioStation;

    @JsonCreator
    public RadioStationResource(@JsonProperty("radioStation") RadioStationResponse radioStation,
                                 @JsonProperty("_links") Map<String, Link> links) {
        this.radioStation = radioStation;
        links.forEach((label, link) -> add(link.withRel(label)));
    }

    public RadioStationResponse getRadioStation() {
        return radioStation;
    }
}
