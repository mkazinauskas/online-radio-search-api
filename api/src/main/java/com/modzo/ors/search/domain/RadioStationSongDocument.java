package com.modzo.ors.search.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.ZonedDateTime;

@Document(indexName = "online_radio_search_radio_station_songs")
public class RadioStationSongDocument {

    @Id
    @JsonProperty("id")
    private long id;

    @JsonProperty("uniqueId")
    private String uniqueId;

    @JsonProperty("songId")
    private long songId;

    @JsonProperty("songUniqueId")
    private String songUniqueId;

    @JsonProperty("radioStationId")
    private long radioStationId;

    @JsonProperty("radioStationUniqueId")
    private String radioStationUniqueId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("playedTime")
    private ZonedDateTime playedTime;

    RadioStationSongDocument() {
    }

    public RadioStationSongDocument(long id,
                                    String uniqueId,
                                    long songId,
                                    String songUniqueId,
                                    long radioStationId,
                                    String radioStationUniqueId,
                                    String title,
                                    ZonedDateTime playedTime) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.songId = songId;
        this.songUniqueId = songUniqueId;
        this.radioStationId = radioStationId;
        this.radioStationUniqueId = radioStationUniqueId;
        this.title = title;
        this.playedTime = playedTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public String getSongUniqueId() {
        return songUniqueId;
    }

    public void setSongUniqueId(String songUniqueId) {
        this.songUniqueId = songUniqueId;
    }

    public long getRadioStationId() {
        return radioStationId;
    }

    public void setRadioStationId(long radioStationId) {
        this.radioStationId = radioStationId;
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
