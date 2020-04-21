package com.modzo.ors.stations.resources.radio.station.stream.urls;

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RadioStationStreamUrlModel extends EntityModel<RadioStationStreamUrlResponse> {

    private RadioStationStreamUrlModel() {
    }

    private RadioStationStreamUrlModel(RadioStationStreamUrlResponse content, Link... links) {
        super(content, links);
    }

    static RadioStationStreamUrlModel create(long radioStationId, long streamUrl, StreamUrl url) {
        RadioStationStreamUrlResponse response = RadioStationStreamUrlResponse.create(url);
        Link link = linkTo(methodOn(RadioStationStreamUrlsController.class)
                .getStreamUrl(radioStationId, streamUrl, url.getId()))
                .withSelfRel();
        return new RadioStationStreamUrlModel(response, link);
    }
}
