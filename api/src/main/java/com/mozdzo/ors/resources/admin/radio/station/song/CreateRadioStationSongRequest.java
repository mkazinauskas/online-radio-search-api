package com.mozdzo.ors.resources.admin.radio.station.song;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class CreateRadioStationSongRequest {
    @Min(1L)
    private long songId;

    @NotNull
    private ZonedDateTime playedTime;

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public ZonedDateTime getPlayedTime() {
        return playedTime;
    }

    public void setPlayedTime(ZonedDateTime playedTime) {
        this.playedTime = playedTime;
    }
}
