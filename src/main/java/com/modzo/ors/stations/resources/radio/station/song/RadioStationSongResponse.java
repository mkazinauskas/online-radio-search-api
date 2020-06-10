package com.modzo.ors.stations.resources.radio.station.song;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;

import java.time.ZonedDateTime;
import java.util.UUID;

class RadioStationSongResponse {

    private final long id;

    private final UUID uniqueId;

    private final ZonedDateTime created;

    private final long songId;

    private final UUID songUniqueId;

    private final String title;

    private final ZonedDateTime playedTime;

    @JsonCreator
    private RadioStationSongResponse(@JsonProperty("id") long id,
                                     @JsonProperty("uniqueId") UUID uniqueId,
                                     @JsonProperty("created") ZonedDateTime created,
                                     @JsonProperty("songId") long songId,
                                     @JsonProperty("songUniqueId") UUID songUniqueId,
                                     @JsonProperty("title") String title,
                                     @JsonProperty("playedTime") ZonedDateTime playedTime) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.created = created;
        this.songId = songId;
        this.songUniqueId = songUniqueId;
        this.title = title;
        this.playedTime = playedTime;
    }

    static RadioStationSongResponse create(RadioStationSong radioStationSong) {
        return new RadioStationSongResponse(
                radioStationSong.getId(),
                radioStationSong.getUniqueId(),
                radioStationSong.getCreated(),
                radioStationSong.getSongId(),
                radioStationSong.getSong().getUniqueId(),
                radioStationSong.getSong().getTitle(),
                radioStationSong.getPlayedTime()
        );
    }

    public long getId() {
        return id;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public long getSongId() {
        return songId;
    }

    public UUID getSongUniqueId() {
        return songUniqueId;
    }

    public String getTitle() {
        return title;
    }

    public ZonedDateTime getPlayedTime() {
        return playedTime;
    }
}
