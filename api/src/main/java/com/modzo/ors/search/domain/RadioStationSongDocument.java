package com.modzo.ors.search.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.ZonedDateTime;

@Document(indexName = "online_radio_search", type = "radio_station_song")
public class RadioStationSongDocument {
    @Id
    @JsonProperty("uniqueId")
    private String uniqueId;

    @JsonProperty("radioStationUniqueId")
    private String radioStationUniqueId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("playedTime")
    private ZonedDateTime playedTime;

    RadioStationSongDocument() {
    }

    public RadioStationSongDocument(String uniqueId, String radioStationUniqueId,
                                    String title, ZonedDateTime playedTime) {
        this.uniqueId = uniqueId;
        this.radioStationUniqueId = radioStationUniqueId;
        this.title = title;
        this.playedTime = playedTime;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getRadioStationUniqueId() {
        return radioStationUniqueId;
    }

    public void setRadioStationUniqueId(String radioStationUniqueId) {
        this.radioStationUniqueId = radioStationUniqueId;
    }

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