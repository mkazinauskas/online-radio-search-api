package com.mozdzo.ors.resources.radio.station.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mozdzo.ors.domain.radio.station.song.RadioStationSong;
import com.mozdzo.ors.domain.song.Song;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class RadioStationSongResource extends ResourceSupport {

    private final RadioStationSongResponse song;

    @JsonCreator
    private RadioStationSongResource(@JsonProperty("song") RadioStationSongResponse song,
                                     @JsonProperty("_links") Map<String, Link> links) {
        this.song = song;
        links.forEach((label, link) -> add(link.withRel(label)));
    }

    static RadioStationSongResource create(long radioStationId, RadioStationSong radioStationSong) {
        RadioStationSongResponse response = RadioStationSongResponse.create(radioStationSong);
        Link link = linkTo(methodOn(RadioStationSongsController.class)
                .getSong(radioStationId, response.getId()))
                .withSelfRel();
        return new RadioStationSongResource(response, singletonMap(link.getRel(), link));
    }

    public RadioStationSongResponse getSong() {
        return song;
    }
}
