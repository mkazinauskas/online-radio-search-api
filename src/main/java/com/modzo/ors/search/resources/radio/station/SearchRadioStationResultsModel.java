package com.modzo.ors.search.resources.radio.station;

import com.modzo.ors.stations.domain.radio.station.RadioStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class SearchRadioStationResultsModel extends PagedModel<SearchRadioStationResultResponse> {

    private SearchRadioStationResultsModel() {
    }

    private SearchRadioStationResultsModel(
            Collection<SearchRadioStationResultResponse> content,
            PageMetadata metadata,
            Link... links) {
        super(content, metadata, links);
    }

    static SearchRadioStationResultsModel create(
            Page<RadioStation> stations,
            Pageable pageable,
            String query) {
        PageMetadata pageMetadata = new PageMetadata(
                stations.getSize(),
                stations.getNumber(),
                stations.getTotalElements(),
                stations.getTotalPages()
        );
        Collection<SearchRadioStationResultResponse> resources = stations.getContent()
                .stream()
                .map(SearchRadioStationResultResponse::create)
                .collect(toList());

        Link link = linkTo(methodOn(SearchRadioStationController.class)
                .search(query, pageable)).withSelfRel();

        return new SearchRadioStationResultsModel(
                resources,
                pageMetadata,
                link
        );
    }
}
