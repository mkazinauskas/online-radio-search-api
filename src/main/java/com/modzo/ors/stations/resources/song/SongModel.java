package com.modzo.ors.stations.resources.song;

import com.modzo.ors.stations.domain.song.Song;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class SongModel extends EntityModel<SongResponse> {

    private SongModel() {
    }

    private SongModel(SongResponse content, Link... links) {
        super(content, links);
    }

    static SongModel create(Song song) {
        SongResponse response = SongResponse.create(song);
        Link link = linkTo(methodOn(SongController.class)
                .getSong(response.getId()))
                .withSelfRel();
        return new SongModel(response, link);
    }
}
