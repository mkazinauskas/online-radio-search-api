package com.mozdzo.ors.resources.radio.station.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mozdzo.ors.domain.radio.station.song.Song;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class SongResource extends ResourceSupport {

    private final SongResponse song;

    @JsonCreator
    private SongResource(@JsonProperty("song") SongResponse song,
                         @JsonProperty("_links") Map<String, Link> links) {
        this.song = song;
        links.forEach((label, link) -> add(link.withRel(label)));
    }

    static SongResource create(long radioStationId, Song song) {
        SongResponse response = SongResponse.create(song);
        Link link = linkTo(methodOn(SongsController.class)
                .getSong(radioStationId, response.getId()))
                .withSelfRel();
        return new SongResource(response, singletonMap(link.getRel(), link));
    }

    public SongResponse getSong() {
        return song;
    }
}
