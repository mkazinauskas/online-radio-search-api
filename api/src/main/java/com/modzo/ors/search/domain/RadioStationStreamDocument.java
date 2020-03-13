package com.modzo.ors.search.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.ZonedDateTime;

@Document(indexName = "online_radio_search_streams")
public class RadioStationStreamDocument {

    @Id
    @JsonProperty("id")
    private long id;

    @JsonProperty("uniqueId")
    private String uniqueId;

    @JsonProperty("radioStationId")
    private long radioStationId;

    @JsonProperty("radioStationUniqueId")
    private String radioStationUniqueId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("bitRate")
    private Integer bitRate;

    @JsonProperty("type")
    private String type;

    @JsonProperty("working")
    private boolean working;

    @JsonProperty("songsChecked")
    private ZonedDateTime songsChecked;

    RadioStationStreamDocument() {
    }

    public RadioStationStreamDocument(long id,
                                      String uniqueId,
                                      long radioStationId,
                                      String radioStationUniqueId,
                                      String url) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.radioStationId = radioStationId;
        this.radioStationUniqueId = radioStationUniqueId;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public long getRadioStationId() {
        return radioStationId;
    }

    public String getRadioStationUniqueId() {
        return radioStationUniqueId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getBitRate() {
        return bitRate;
    }

    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public ZonedDateTime getSongsChecked() {
        return songsChecked;
    }

    public void setSongsChecked(ZonedDateTime songsChecked) {
        this.songsChecked = songsChecked;
    }
}
