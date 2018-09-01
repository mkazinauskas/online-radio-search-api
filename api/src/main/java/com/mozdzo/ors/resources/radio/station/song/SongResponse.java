package com.mozdzo.ors.resources.radio.station.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mozdzo.ors.domain.radio.station.song.Song;

import java.time.LocalDateTime;

public class SongResponse {

    private final long id;

    private final String title;

    private final LocalDateTime playingTime;

    @JsonCreator
    private SongResponse(@JsonProperty("id") long id,
                         @JsonProperty("title") String title,
                         @JsonProperty("playingTime") LocalDateTime playingTime) {
        this.id = id;
        this.title = title;
        this.playingTime = playingTime;
    }

    static SongResponse create(Song song) {
        return new SongResponse(song.getId(), song.getTitle(), song.getPlayingTime());
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getPlayingTime() {
        return playingTime;
    }
}
