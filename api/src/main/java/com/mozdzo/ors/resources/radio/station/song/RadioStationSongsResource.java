package com.mozdzo.ors.resources.radio.station.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mozdzo.ors.domain.radio.station.song.RadioStationSong;
import com.mozdzo.ors.domain.radio.station.song.RadioStationSongs;
import com.mozdzo.ors.domain.song.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;

import java.util.Collection;
import java.util.Map;

import static com.mozdzo.ors.resources.HateoasHelper.parseLinks;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

class RadioStationSongsResource extends PagedResources<RadioStationSongResource> {

    @JsonCreator
    private RadioStationSongsResource(
            @JsonProperty("_embedded") Map<String, Collection<RadioStationSongResource>> content,
            @JsonProperty("page") PageMetadata metadata,
            @JsonProperty("_links") Map<String, Link> links) {
        super(content.get("songResourceList"), metadata, parseLinks(links));
    }

    static RadioStationSongsResource create(Page<RadioStationSong> songs, long radioStationId, Pageable pageable) {
        PageMetadata pageMetadata = new PageMetadata(
                songs.getSize(),
                songs.getNumber(),
                songs.getTotalElements(),
                songs.getTotalPages()
        );
        Collection<RadioStationSongResource> resources = songs.getContent()
                .stream()
                .map((RadioStationSong radioStationSong) -> RadioStationSongResource.create(radioStationId, radioStationSong))
                .collect(toList());

        Link link = linkTo(methodOn(RadioStationSongsController.class)
                .getSongs(radioStationId, pageable)).withSelfRel();

        return new RadioStationSongsResource(singletonMap("songResourceList", resources),
                pageMetadata,
                singletonMap(link.getRel(), link)
        );
    }
}
