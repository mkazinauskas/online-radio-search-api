package com.modzo.ors.search.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Document(indexName = "online_radio_search_radio_stations")
public class RadioStationDocument {

    @Id
    @JsonProperty("id")
    private long id;

    @JsonProperty("uniqueId")
    private String uniqueId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("website")
    private String website;

    @Field(type = FieldType.Nested)
    @JsonProperty("streams")
    private List<RadioStationStreamDocument> streams = new ArrayList<>();

    @Field(type = FieldType.Nested)
    @JsonProperty("songs")
    private List<RadioStationSongDocument> songs = new ArrayList<>();

    @Field(type = FieldType.Nested)
    @JsonProperty("genres")
    private Set<GenreDocument> genres = new LinkedHashSet<>();

    RadioStationDocument() {
    }

    public RadioStationDocument(long id, String uniqueId, String title) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Set<GenreDocument> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreDocument> genres) {
        this.genres = new LinkedHashSet<>(genres);
    }

    public void removeStream(String streamId) {
        this.streams.removeIf(stream -> stream.getUniqueId().equals(streamId));
    }

    public void removeSong(String songUniqueId) {
        this.songs.removeIf(song -> song.getUniqueId().equals(songUniqueId));
    }
}
