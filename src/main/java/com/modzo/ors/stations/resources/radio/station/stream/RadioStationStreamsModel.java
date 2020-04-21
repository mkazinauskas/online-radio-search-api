package com.modzo.ors.stations.resources.radio.station.stream;

import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class RadioStationStreamsModel extends PagedModel<RadioStationStreamModel> {

    private RadioStationStreamsModel() {
    }

    private RadioStationStreamsModel(Collection<RadioStationStreamModel> content,
                                     PageMetadata metadata,
                                     Link... links) {
        super(content, metadata, links);
    }

    static RadioStationStreamsModel create(Page<RadioStationStream> radioStations,
                                           long radioStationId,
                                           Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                radioStations.getSize(),
                radioStations.getNumber(),
                radioStations.getTotalElements(),
                radioStations.getTotalPages()
        );
        List<RadioStationStreamModel> resources = radioStations.getContent()
                .stream()
                .map((RadioStationStream stream) -> RadioStationStreamModel.create(radioStationId, stream))
                .collect(toList());

        Link link = linkTo(methodOn(RadioStationStreamController.class)
                .getRadioStationStreams(radioStationId, pageable)).withSelfRel();

        return new RadioStationStreamsModel(resources, pageMetadata, link);
    }
}
