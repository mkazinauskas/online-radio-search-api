package com.modzo.ors.stations.resources.radio.station.song;

import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import com.modzo.ors.stations.domain.radio.station.song.commands.GetRadioStationSongByid;
import com.modzo.ors.stations.domain.radio.station.song.commands.GetRadioStationSongs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class RadioStationSongsController {

    private final GetRadioStationSongByid.Handler songHandler;

    private final GetRadioStationSongs.Handler songsHandler;

    public RadioStationSongsController(GetRadioStationSongByid.Handler songHandler,
                                       GetRadioStationSongs.Handler songsHandler) {
        this.songHandler = songHandler;
        this.songsHandler = songsHandler;
    }

    @GetMapping("/radio-stations/{radioStationId}/songs")
    ResponseEntity<PagedModel<RadioStationSongResource>> getSongs(
            @PathVariable("radioStationId") long radioStationId,
            Pageable pageable
    ) {
        Page<RadioStationSong> songs = songsHandler.handle(
                new GetRadioStationSongs(radioStationId, pageable)
        );
        return ok(RadioStationSongsResource.create(songs, radioStationId, pageable));
    }

    @GetMapping("/radio-stations/{radioStationId}/songs/{radioStationSongId}")
    ResponseEntity<RadioStationSongResource> getSong(@PathVariable("radioStationId") long radioStationId,
                                                     @PathVariable("radioStationSongId") long radioStationSongId) {
        RadioStationSong song = songHandler.handle(new GetRadioStationSongByid(radioStationId, radioStationSongId));
        return ok(RadioStationSongResource.create(radioStationId, song));
    }
}