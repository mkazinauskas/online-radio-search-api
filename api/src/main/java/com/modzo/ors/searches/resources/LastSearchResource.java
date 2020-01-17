package com.modzo.ors.searches.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.searches.domain.SearchedQuery;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class LastSearchResource extends ResourceSupport {

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
        return new LastSearchResource(response, singletonMap(link.getRel(), link));
    }

    public LastSearchResponse getLastSearch() {
        return lastSearch;
    }
}
