package com.modzo.ors.stations.resources.genre;

import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import com.modzo.ors.stations.domain.song.Song;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class GenreModel extends EntityModel<GenreResponse> {

    private GenreModel() {
    }

    private GenreModel(GenreResponse content, Link... links) {
        super(content, links);
    }

    static GenreModel create(Genre genre) {
        GenreResponse response = GenreResponse.create(genre);
        Link link = linkTo(methodOn(GenreController.class)
                .getGenre(response.getId()))
                .withSelfRel();
        return new GenreModel(response, link);
    }
}
