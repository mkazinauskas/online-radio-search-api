package com.modzo.ors.stations.resources.radio.station.song;

import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class RadioStationSongModel extends EntityModel<RadioStationSongResponse> {

    private RadioStationSongModel() {
    }

    private RadioStationSongModel(RadioStationSongResponse content, Link... links) {
        super(content, links);
    }

    static RadioStationSongModel create(long radioStationId, RadioStationSong radioStationSong) {
        RadioStationSongResponse response = RadioStationSongResponse.create(radioStationSong);
        Link link = linkTo(methodOn(RadioStationSongsController.class)
                .getSong(radioStationId, response.getId()))
                .withSelfRel();
        return new RadioStationSongModel(response, link);
    }
}
