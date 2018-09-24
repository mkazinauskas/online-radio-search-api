package com.mozdzo.ors.resources.admin.radio.station.song;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class CreateSongRequest {
    @NotBlank
    private String title;

    @NotNull
    private ZonedDateTime playedTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getPlayedTime() {
        return playedTime;
    }

    public void setPlayedTime(ZonedDateTime playedTime) {
        this.playedTime = playedTime;
    }
}
