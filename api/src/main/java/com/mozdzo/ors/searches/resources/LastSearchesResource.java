package com.mozdzo.ors.searches.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mozdzo.ors.searches.domain.SearchedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static com.mozdzo.ors.resources.HateoasHelper.parseLinks;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

class LastSearchesResource extends PagedResources<LastSearchResource> {

    @JsonCreator
    private LastSearchesResource(
            @JsonProperty("_embedded") Map<String, Collection<LastSearchResource>> content,
            @JsonProperty("page") PageMetadata metadata,
            @JsonProperty("_links") Map<String, Link> links) {
        super(resolve(content), metadata, parseLinks(links));
    }

    private static Collection<LastSearchResource> resolve(
            Map<String, Collection<LastSearchResource>> content
    ) {
        return CollectionUtils.isEmpty(content)
                ? Collections.emptyList()
                : content.get("lastSearchResourceList");
    }

    static LastSearchesResource create(Page<SearchedQuery> queries, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                queries.getSize(),
                queries.getNumber(),
                queries.getTotalElements(),
                queries.getTotalPages()
        );
        Collection<LastSearchResource> resources = queries.getContent()
                .stream()
                .map(LastSearchResource::create)
                .collect(toList());

        Link link = linkTo(methodOn(LastSearchesController.class)
                .getLastSearches(pageable)).withSelfRel();

        return new LastSearchesResource(singletonMap("lastSearchResourceList", resources),
                pageMetadata,
                singletonMap(link.getRel(), link)
        );
    }
}