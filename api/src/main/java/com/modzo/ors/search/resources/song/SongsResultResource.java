package com.modzo.ors.search.resources.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.search.domain.SongDocument;
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

class SongsResultResource extends PagedResources<SongResultResource> {

    @JsonCreator
    private SongsResultResource(
            @JsonProperty("_embedded") Map<String, Collection<SongResultResource>> content,
            @JsonProperty("page") PageMetadata metadata,
            @JsonProperty("_links") Map<String, Link> links) {
        super(content.get("songResourceList"), metadata, parseLinks(links));
    }

    static SongsResultResource create(Page<SongDocument> songs, Pageable pageable) {
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

        Link link = linkTo(methodOn(SongSearchController.class)
                .search("", pageable)).withSelfRel();

        return new SongsResultResource(singletonMap("songResourceList", resources),
                pageMetadata,
                singletonMap(link.getRel(), link)
        );
    }
}
