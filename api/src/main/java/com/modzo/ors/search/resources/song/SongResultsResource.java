package com.modzo.ors.search.resources.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.search.domain.SongDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.Map;

import static com.modzo.ors.stations.resources.HateoasHelper.parseLinks;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class SongResultsResource extends PagedModel<SongResultResource> {

    @JsonCreator
    private SongResultsResource(
            @JsonProperty("_embedded") Map<String, Collection<SongResultResource>> content,
            @JsonProperty("page") PageMetadata metadata,
            @JsonProperty("_links") Map<String, Link> links) {
        super(content.get("songResourceList"), metadata, parseLinks(links));
    }

    static SongResultsResource create(Page<SongDocument> songs, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                songs.getSize(),
                songs.getNumber(),
                songs.getTotalElements(),
                songs.getTotalPages()
        );
        Collection<SongResultResource> resources = songs.getContent()
                .stream()
                .map(SongResultResource::create)
                .collect(toList());

        Link link = linkTo(methodOn(SearchSongController.class)
                .search("", pageable)).withSelfRel();

        return new SongResultsResource(singletonMap("songResourceList", resources),
                pageMetadata,
                singletonMap(link.getRel().value(), link)
        );
    }
}
