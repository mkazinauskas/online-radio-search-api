package com.modzo.ors.stations.resources.radio.station.stream;

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RadioStationStreamModel extends EntityModel<RadioStationStreamResponse> {

    private RadioStationStreamModel() {
    }

    private RadioStationStreamModel(RadioStationStreamResponse content, Link... links) {
        super(content, links);
    }

    static RadioStationStreamModel create(long radioStationId, RadioStationStream radioStationStream) {
        RadioStationStreamResponse response = RadioStationStreamResponse.create(radioStationStream);
        Link link = linkTo(methodOn(RadioStationStreamController.class)
                .getRadioStationStream(radioStationId, response.getId()))
                .withSelfRel();
        return new RadioStationStreamModel(response, link);
    }
}
