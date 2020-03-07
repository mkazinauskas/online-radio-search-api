package com.modzo.ors.web.web.api.radio.stations.songs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class RadioStationSongResponse {

    private final long id;

    private final long songId;

    private final String title;

    private final ZonedDateTime playedTime;

    @JsonCreator
    private RadioStationSongResponse(@JsonProperty("id") long id,
                                     @JsonProperty("songId") long songId,
                                     @JsonProperty("title") String title,
                                     @JsonProperty("playedTime") ZonedDateTime playedTime) {
        this.id = id;
        this.songId = songId;
        this.title = title;
        this.playedTime = playedTime;
    }

    public long getId() {
        return id;
    }

    public long getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public ZonedDateTime getPlayedTime() {
        return playedTime;
    }
}
