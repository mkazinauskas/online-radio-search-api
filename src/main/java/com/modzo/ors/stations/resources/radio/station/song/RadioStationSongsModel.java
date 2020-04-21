package com.modzo.ors.stations.resources.radio.station.song;

import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class RadioStationSongsModel extends PagedModel<RadioStationSongModel> {

    private RadioStationSongsModel() {
    }

    private RadioStationSongsModel(Collection<RadioStationSongModel> content, PageMetadata metadata, Link... links) {
        super(content, metadata, links);
    }

    private static Collection<RadioStationSongModel> resolve(
            Map<String, Collection<RadioStationSongModel>> content
    ) {
        return CollectionUtils.isEmpty(content)
                ? Collections.emptyList()
                : content.get("radioStationSongResourceList");
    }

    static RadioStationSongsModel create(Page<RadioStationSong> songs, long radioStationId, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                songs.getSize(),
                songs.getNumber(),
                songs.getTotalElements(),
                songs.getTotalPages()
        );
        Collection<RadioStationSongModel> resources = songs.getContent()
                .stream()
                .map((RadioStationSong radioStationSong) ->
                        RadioStationSongModel.create(radioStationId, radioStationSong))
                .collect(toList());

        Link link = linkTo(methodOn(RadioStationSongsController.class)
                .getSongs(radioStationId, pageable)).withSelfRel();

        return new RadioStationSongsModel(
                resources,
                pageMetadata,
                link
        );
    }
}
