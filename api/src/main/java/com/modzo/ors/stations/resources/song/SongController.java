package com.modzo.ors.stations.resources.song;

import com.modzo.ors.stations.domain.song.Song;
import com.modzo.ors.stations.domain.song.commands.GetSong;
import com.modzo.ors.stations.domain.song.commands.GetSongs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class SongController {

    private final GetSong.Handler getSongHandler;

    private final GetSongs.Handler getSongsHandler;

    SongController(GetSong.Handler getSongHandler, GetSongs.Handler getSongsHandler) {
        this.getSongHandler = getSongHandler;
        this.getSongsHandler = getSongsHandler;
    }

    @GetMapping("/songs")
    ResponseEntity<SongsResource> getSongs(Pageable pageable) {
        Page<Song> foundSongs = getSongsHandler.handle(new GetSongs(pageable));
        return ok(SongsResource.create(foundSongs, pageable));
    }

    @GetMapping("/songs/{id}")
    ResponseEntity<SongResource> getSong(@PathVariable("id") long id) {
        Song foundSong = getSongHandler.handle(new GetSong(id));
        return ok(SongResource.create(foundSong));
    }
}