package com.modzo.ors.stations.resources.radio.station.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class RadioStationSongResource extends RepresentationModel {

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
        return new RadioStationSongResource(response, singletonMap(link.getRel().value(), link));
    }

    public RadioStationSongResponse getRadioStationSong() {
        return radioStationSong;
    }
}
