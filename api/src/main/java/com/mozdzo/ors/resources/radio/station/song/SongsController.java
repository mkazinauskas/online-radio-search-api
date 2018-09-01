package com.mozdzo.ors.resources.radio.station.song;

import com.mozdzo.ors.domain.radio.station.song.Song;
import com.mozdzo.ors.domain.radio.station.song.commands.GetSong;
import com.mozdzo.ors.domain.radio.station.song.commands.GetSongs;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.commands.GetRadioStationStream;
import com.mozdzo.ors.domain.radio.station.stream.commands.GetRadioStationStreams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class SongsController {

    private final GetSong.Handler songHandler;

    private final GetSongs.Handler songsHandler;

    public SongsController(GetSong.Handler songHandler, GetSongs.Handler songsHandler) {
        this.songHandler = songHandler;
        this.songsHandler = songsHandler;
    }

    @GetMapping("/radio-stations/{radioStationId}/songs")
    ResponseEntity<PagedResources<SongResource>> getSongs(
            @PathVariable("radioStationId") long radioStationId,
            Pageable pageable
    ) {
        Page<Song> songs = songsHandler.handle(
                new GetSongs(radioStationId, pageable)
        );
        return ok(SongsResource.create(songs, radioStationId, pageable));
    }

    @GetMapping("/radio-stations/{radioStationId}/songs/{songId}")
    ResponseEntity<SongResource> getSong(@PathVariable("radioStationId") long radioStationId,
                                         @PathVariable("songId") long songId) {
        Song song = songHandler.handle(new GetSong(radioStationId, songId));
        return ok(SongResource.create(radioStationId, song));
    }
}