package com.modzo.ors.web.components.common.model;

import com.modzo.ors.web.api.radio.stations.songs.RadioStationSongResponse;
import com.modzo.ors.web.utils.SeoText;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class RadioStationSongModel {

    private static final DateTimeFormatter PLAYED_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd - HH:mm:ss z");

    private final long id;

    private final long songId;

    private final String title;

    private final String seoTitle;

    private final ZonedDateTime playedTime;

    private final String playedTimeAsString;

    public RadioStationSongModel(RadioStationSongResponse response) {
        this.id = response.getId();
        this.songId = response.getSongId();
        this.title = response.getTitle();
        this.seoTitle = SeoText.from(response.getTitle());
        this.playedTime = response.getPlayedTime();
        this.playedTimeAsString = this.playedTime.format(PLAYED_TIME_FORMATTER);
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

    public String getSeoTitle() {
        return seoTitle;
    }

    public ZonedDateTime getPlayedTime() {
        return playedTime;
    }

    public String getPlayedTimeAsString() {
        return playedTimeAsString;
    }
}
