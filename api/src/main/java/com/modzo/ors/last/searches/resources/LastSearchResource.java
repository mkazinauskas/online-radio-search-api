package com.modzo.ors.last.searches.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.last.searches.domain.SearchedQuery;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LastSearchResource extends RepresentationModel {

    private final LastSearchResponse lastSearch;

    @JsonCreator
    private LastSearchResource(@JsonProperty("lastSearch") LastSearchResponse lastSearch,
                               @JsonProperty("_links") Map<String, Link> links) {
        this.lastSearch = lastSearch;
        links.forEach((label, link) -> add(link.withRel(label)));
    }

    static LastSearchResource create(SearchedQuery searchedQuery) {
        LastSearchResponse response = LastSearchResponse.create(searchedQuery);
        Link link = linkTo(methodOn(LastSearchesController.class)
                .getLastSearches(null))
                .withSelfRel();
        return new LastSearchResource(response, singletonMap(link.getRel().value(), link));
    }

    public LastSearchResponse getLastSearch() {
        return lastSearch;
    }
}
