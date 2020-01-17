package com.modzo.ors.resources.radio.station.stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.domain.radio.station.stream.RadioStationStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;

import java.util.Collection;
import java.util.Map;

import static com.modzo.ors.resources.HateoasHelper.parseLinks;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

class RadioStationStreamsResource extends PagedResources<RadioStationStreamResource> {

    @JsonCreator
    private RadioStationStreamsResource(
            @JsonProperty("_embedded") Map<String, Collection<RadioStationStreamResource>> content,
            @JsonProperty("page") PageMetadata metadata,
            @JsonProperty("_links") Map<String, Link> links) {
        super(content.get("radioStationStreamResourceList"), metadata, parseLinks(links));
    }

    static RadioStationStreamsResource create(Page<RadioStationStream> radioStations,
                                              long radioStationId,
                                              Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                radioStations.getSize(),
                radioStations.getNumber(),
                radioStations.getTotalElements(),
                radioStations.getTotalPages()
        );
        Collection<RadioStationStreamResource> resources = radioStations.getContent()
                .stream()
                .map((RadioStationStream stream) -> RadioStationStreamResource.create(radioStationId, stream))
                .collect(toList());

        Link link = linkTo(methodOn(RadioStationStreamController.class)
                .getRadioStationStreams(radioStationId, pageable)).withSelfRel();

        return new RadioStationStreamsResource(singletonMap("radioStationStreamResourceList", resources),
                pageMetadata,
                singletonMap(link.getRel(), link)
        );
    }
}
