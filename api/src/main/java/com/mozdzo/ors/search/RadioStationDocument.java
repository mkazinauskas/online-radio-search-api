package com.mozdzo.ors.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;

@Document(indexName = "online_radio_search", type = "radio_station")
public class RadioStationDocument {
    @Id
    private String uniqueId;

    private String title;

    @Field( type = FieldType.Nested)
    private List<RadioStationStreamDocument> streams = new ArrayList<>();

    @Field( type = FieldType.Nested)
    private List<RadioStationSongDocument> songs = new ArrayList<>();

    RadioStationDocument() {
    }

    public RadioStationDocument(String uniqueId, String title) {
        this.uniqueId = uniqueId;
        this.title = title;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RadioStationStreamDocument> getStreams() {
        return streams;
    }

    public void setStreams(List<RadioStationStreamDocument> streams) {
        this.streams = streams;
    }

    public List<RadioStationSongDocument> getSongs() {
        return songs;
    }

    public void setSongs(List<RadioStationSongDocument> songs) {
        this.songs = songs;
    }
}
