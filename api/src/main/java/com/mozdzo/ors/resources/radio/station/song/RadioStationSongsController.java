package com.mozdzo.ors.resources.radio.station.song;

import com.mozdzo.ors.domain.radio.station.song.RadioStationSong;
import com.mozdzo.ors.domain.radio.station.song.commands.GetRadioStationSong;
import com.mozdzo.ors.domain.radio.station.song.commands.GetRadioStationSongs;
import com.mozdzo.ors.domain.song.Song;
import com.mozdzo.ors.domain.song.commands.GetSong;
import com.mozdzo.ors.domain.song.commands.GetSongs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class RadioStationSongsController {

    private final GetRadioStationSong.Handler songHandler;

    private final GetRadioStationSongs.Handler songsHandler;

    public RadioStationSongsController(GetRadioStationSong.Handler songHandler,
                                       GetRadioStationSongs.Handler songsHandler) {
        this.songHandler = songHandler;
        this.songsHandler = songsHandler;
    }

    @GetMapping("/radio-stations/{radioStationId}/songs")
    ResponseEntity<PagedResources<RadioStationSongResource>> getSongs(
            @PathVariable("radioStationId") long radioStationId,
            Pageable pageable
    ) {
        Page<RadioStationSong> songs = songsHandler.handle(
                new GetRadioStationSongs(radioStationId, pageable)
        );
        return ok(RadioStationSongsResource.create(songs, radioStationId, pageable));
    }

    @GetMapping("/radio-stations/{radioStationId}/songs/{songId}")
    ResponseEntity<RadioStationSongResource> getSong(@PathVariable("radioStationId") long radioStationId,
                                                     @PathVariable("songId") long songId) {
        RadioStationSong song = songHandler.handle(new GetRadioStationSong(radioStationId, songId));
        return ok(RadioStationSongResource.create(radioStationId, song));
    }
}