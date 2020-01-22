package com.modzo.ors.search.resources.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.search.domain.SongDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class SongResultResource extends RepresentationModel {

    private final SongResultResponse song;

    @JsonCreator
    private SongResultResource(@JsonProperty("song") SongResultResponse song,
                               @JsonProperty("_links") Map<String, Link> links) {
        this.song = song;
        links.forEach((label, link) -> add(link.withRel(label)));
    }

    static SongResultResource create(SongDocument song) {
        SongResultResponse response = SongResultResponse.create(song);
        Link link = linkTo(methodOn(SongSearchController.class)
                .search("", Pageable.unpaged()))
                .withSelfRel();
        return new SongResultResource(response, singletonMap(link.getRel().value(), link));
    }

    public SongResultResponse getSong() {
        return song;
    }
}
