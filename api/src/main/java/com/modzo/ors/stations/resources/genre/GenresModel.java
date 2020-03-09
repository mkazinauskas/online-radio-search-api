package com.modzo.ors.stations.resources.genre;

import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class GenresModel extends PagedModel<GenreModel> {

    private GenresModel() {
    }

    private GenresModel(Collection<GenreModel> content, PageMetadata metadata, Link... links) {
        super(content, metadata, links);
    }

    static GenresModel create(Page<Genre> genres, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                genres.getSize(),
                genres.getNumber(),
                genres.getTotalElements(),
                genres.getTotalPages()
        );
        Collection<GenreModel> resources = genres.getContent()
                .stream()
                .map(GenreModel::create)
                .collect(toList());

        Link link = linkTo(methodOn(GenreController.class)
                .getGenres(pageable)).withSelfRel();

        return new GenresModel(
                resources,
                pageMetadata,
                link
        );
    }
}
