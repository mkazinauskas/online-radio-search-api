package com.modzo.ors.stations.resources.song;

import com.modzo.ors.stations.domain.song.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class SongsModel extends PagedModel<SongModel> {

    private SongsModel() {
    }

    private SongsModel(Collection<SongModel> content, PageMetadata metadata, Link... links) {
        super(content, metadata, links);
    }

    static SongsModel create(Page<Song> songs, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                songs.getSize(),
                songs.getNumber(),
                songs.getTotalElements(),
                songs.getTotalPages()
        );
        Collection<SongModel> resources = songs.getContent()
                .stream()
                .map(SongModel::create)
                .collect(toList());

        Link link = linkTo(methodOn(SongController.class)
                .getSongs(pageable)).withSelfRel();

        return new SongsModel(
                resources,
                pageMetadata,
                link
        );
    }
}
