package com.modzo.ors.stations.resources.radio.station.stream.urls;

import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class RadioStationStreamUrlsModel extends PagedModel<RadioStationStreamUrlModel> {

    private RadioStationStreamUrlsModel() {
    }

    private RadioStationStreamUrlsModel(Collection<RadioStationStreamUrlModel> content,
                                        PageMetadata metadata,
                                        Link... links) {
        super(content, metadata, links);
    }

    static RadioStationStreamUrlsModel create(Page<StreamUrl> url,
                                              long radioStationId,
                                              long streamId,
                                              Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                url.getSize(),
                url.getNumber(),
                url.getTotalElements(),
                url.getTotalPages()
        );
        List<RadioStationStreamUrlModel> resources = url.getContent()
                .stream()
                .map((StreamUrl streamUrl) -> RadioStationStreamUrlModel.create(radioStationId, streamId, streamUrl))
                .collect(toList());

        Link link = linkTo(methodOn(RadioStationStreamUrlsController.class)
                .getStreamUrls(radioStationId, streamId, pageable)).withSelfRel();

        return new RadioStationStreamUrlsModel(resources, pageMetadata, link);
    }
}
