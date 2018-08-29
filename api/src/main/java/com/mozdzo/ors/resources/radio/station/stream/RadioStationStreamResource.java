package com.mozdzo.ors.resources.radio.station.stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class RadioStationStreamResource extends ResourceSupport {

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
        return new RadioStationStreamResource(response, singletonMap(link.getRel(), link));
    }

    public RadioStationStreamResponse getRadioStationStream() {
        return radioStationStream;
    }
}
