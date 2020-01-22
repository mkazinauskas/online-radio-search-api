package com.modzo.ors.resources.radio.station;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.domain.radio.station.RadioStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.Map;

import static com.modzo.ors.resources.HateoasHelper.parseLinks;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class RadioStationsResource extends PagedModel<RadioStationResource> {

    @JsonCreator
    RadioStationsResource(
            @JsonProperty("_embedded") Map<String, Collection<RadioStationResource>> content,
            @JsonProperty("page") PageMetadata metadata,
            @JsonProperty("_links") Map<String, Link> links) {
        super(content.get("radioStationResourceList"), metadata, parseLinks(links));
    }

    static RadioStationsResource create(Page<RadioStation> radioStations, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                radioStations.getSize(),
                radioStations.getNumber(),
                radioStations.getTotalElements(),
                radioStations.getTotalPages()
        );
        Collection<RadioStationResource> resources = radioStations.getContent()
                .stream()
                .map(RadioStationResource::create)
                .collect(toList());

        Link link = linkTo(methodOn(RadioStationController.class)
                .getRadioStations(pageable)).withSelfRel();

        return new RadioStationsResource(singletonMap("radioStationResourceList", resources),
                pageMetadata,
                singletonMap(link.getRel().value(), link)
        );
    }
}
