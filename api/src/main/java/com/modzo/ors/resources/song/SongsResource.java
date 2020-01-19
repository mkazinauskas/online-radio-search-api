package com.modzo.ors.resources.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.domain.song.Song;
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

class SongsResource extends PagedModel<SongResource> {

    @JsonCreator
    private SongsResource(
            @JsonProperty("_embedded") Map<String, Collection<SongResource>> content,
            @JsonProperty("page") PageMetadata metadata,
            @JsonProperty("_links") Map<String, Link> links) {
        super(content.get("songResourceList"), metadata, parseLinks(links));
    }

    static SongsResource create(Page<Song> songs, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                songs.getSize(),
                songs.getNumber(),
                songs.getTotalElements(),
                songs.getTotalPages()
        );
        Collection<SongResource> resources = songs.getContent()
                .stream()
                .map(SongResource::create)
                .collect(toList());

        Link link = linkTo(methodOn(SongController.class)
                .getSongs(pageable)).withSelfRel();

        return new SongsResource(singletonMap("songResourceList", resources),
                pageMetadata,
                singletonMap(link.getRel().value(), link)
        );
    }
}
