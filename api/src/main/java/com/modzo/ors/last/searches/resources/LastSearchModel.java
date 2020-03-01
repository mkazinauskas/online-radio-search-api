package com.modzo.ors.last.searches.resources;

import com.modzo.ors.last.searches.domain.SearchedQuery;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class LastSearchModel extends EntityModel<LastSearchResponse> {

    private LastSearchModel() {
    }

    private LastSearchModel(LastSearchResponse content, Link... links) {
        super(content, links);
    }

    static LastSearchModel create(SearchedQuery searchedQuery) {
        LastSearchResponse response = LastSearchResponse.create(searchedQuery);
        Link link = linkTo(methodOn(LastSearchesController.class)
                .getLastSearches(null))
                .withSelfRel();
        return new LastSearchModel(response, link);
    }
}
