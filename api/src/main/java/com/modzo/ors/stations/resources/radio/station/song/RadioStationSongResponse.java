package com.modzo.ors.stations.resources.radio.station.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;

import java.time.ZonedDateTime;

class RadioStationSongResponse {

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

    static RadioStationSongResponse create(RadioStationSong radioStationSong) {
        return new RadioStationSongResponse(
                radioStationSong.getId(),
                radioStationSong.getSongId(),
                radioStationSong.getSong().getTitle(),
                radioStationSong.getPlayedTime()
        );
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
