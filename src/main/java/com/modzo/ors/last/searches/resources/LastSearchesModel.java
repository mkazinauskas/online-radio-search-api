package com.modzo.ors.last.searches.resources;

import com.modzo.ors.last.searches.domain.SearchedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class LastSearchesModel extends PagedModel<LastSearchModel> {

    private LastSearchesModel() {
    }

    private LastSearchesModel(Collection<LastSearchModel> content, PageMetadata metadata, Link... links) {
        super(content, metadata, links);
    }

    static LastSearchesModel create(Page<SearchedQuery> queries, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                queries.getSize(),
                queries.getNumber(),
                queries.getTotalElements(),
                queries.getTotalPages()
        );
        Collection<LastSearchModel> resources = queries.getContent()
                .stream()
                .map(LastSearchModel::create)
                .collect(toList());

        Link link = linkTo(methodOn(LastSearchesController.class)
                .getLastSearches(pageable)).withSelfRel();

        return new LastSearchesModel(
                resources,
                pageMetadata,
                link
        );
    }
}
