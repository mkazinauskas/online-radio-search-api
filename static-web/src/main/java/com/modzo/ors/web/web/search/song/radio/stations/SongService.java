package com.modzo.ors.web.web.search.song.radio.stations;

import com.modzo.ors.web.web.api.songs.SongsClient;
import com.modzo.ors.web.web.components.common.model.SongModel;
import org.springframework.stereotype.Component;

@Component
class SongService {

    private final SongsClient songsClient;

    public SongService(SongsClient songsClient) {
        this.songsClient = songsClient;
    }

    SongModel retrieveSong(long songId) {
        var content = songsClient.getSongById(songId).getContent();
        return new SongModel(content);
    }
}
