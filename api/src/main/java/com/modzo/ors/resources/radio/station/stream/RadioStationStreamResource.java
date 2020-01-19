package com.modzo.ors.resources.radio.station.stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.domain.radio.station.stream.RadioStationStream;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RadioStationStreamResource extends RepresentationModel {

    private final RadioStationStreamResponse radioStationStream;

    @JsonCreator
    private RadioStationStreamResource(@JsonProperty("radioStationStream") RadioStationStreamResponse radioStationStream,
                                       @JsonProperty("_links") Map<String, Link> links) {
        this.radioStationStream = radioStationStream;
        links.forEach((label, link) -> add(link.withRel(label)));
    }

    static RadioStationStreamResource create(long radioStationId, RadioStationStream radioStationStream) {
        RadioStationStreamResponse response = RadioStationStreamResponse.create(radioStationStream);
        Link link = linkTo(methodOn(RadioStationStreamController.class)
                .getRadioStationStream(radioStationId, response.getId()))
                .withSelfRel();
        return new RadioStationStreamResource(response, singletonMap(link.getRel().value(), link));
    }

    public RadioStationStreamResponse getRadioStationStream() {
        return radioStationStream;
    }
}
