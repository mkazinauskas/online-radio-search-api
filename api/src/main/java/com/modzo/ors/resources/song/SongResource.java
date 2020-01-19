package com.modzo.ors.resources.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.domain.song.Song;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class SongResource extends RepresentationModel {

    private final SongResponse song;

    @JsonCreator
    private SongResource(@JsonProperty("song") SongResponse song,
                         @JsonProperty("_links") Map<String, Link> links) {
        this.song = song;
        links.forEach((label, link) -> add(link.withRel(label)));
    }

    static SongResource create(Song song) {
        SongResponse response = SongResponse.create(song);
        Link link = linkTo(methodOn(SongController.class)
                .getSong(response.getId()))
                .withSelfRel();
        return new SongResource(response, singletonMap(link.getRel().value(), link));
    }

    public SongResponse getSong() {
        return song;
    }
}
