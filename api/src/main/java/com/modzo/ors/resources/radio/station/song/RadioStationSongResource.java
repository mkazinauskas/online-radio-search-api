package com.modzo.ors.resources.radio.station.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.domain.radio.station.song.RadioStationSong;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class RadioStationSongResource extends ResourceSupport {

    private final RadioStationSongResponse radioStationSong;

    @JsonCreator
    private RadioStationSongResource(@JsonProperty("radioStationSong") RadioStationSongResponse radioStationSong,
                                     @JsonProperty("_links") Map<String, Link> links) {
        this.radioStationSong = radioStationSong;
        links.forEach((label, link) -> add(link.withRel(label)));
    }

    static RadioStationSongResource create(long radioStationId, RadioStationSong radioStationSong) {
        RadioStationSongResponse response = RadioStationSongResponse.create(radioStationSong);
        Link link = linkTo(methodOn(RadioStationSongsController.class)
                .getSong(radioStationId, response.getId()))
                .withSelfRel();
        return new RadioStationSongResource(response, singletonMap(link.getRel(), link));
    }

    public RadioStationSongResponse getRadioStationSong() {
        return radioStationSong;
    }
}
