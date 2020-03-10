package com.modzo.ors.stations.resources.radio.station;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class RadioStationModel extends EntityModel<RadioStationResponse> {

    private RadioStationModel() {
    }

    private RadioStationModel(RadioStationResponse content, Link... links) {
        super(content, links);
    }

    static RadioStationModel create(RadioStation radioStation) {
        RadioStationResponse response = RadioStationResponse.create(radioStation);
        Link link = linkTo(methodOn(RadioStationController.class)
                .getRadioStation(response.getId())).withSelfRel();
        return new RadioStationModel(response, link);
    }

}
