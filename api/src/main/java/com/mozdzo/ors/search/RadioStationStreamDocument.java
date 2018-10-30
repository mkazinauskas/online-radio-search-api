package com.mozdzo.ors.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "online_radio_search", type = "radio_station_stream")
public class RadioStationStreamDocument {
    @Id
    @JsonProperty("uniqueId")
    private String uniqueId;

    @JsonProperty("radioStationUniqueId")
    private String radioStationUniqueId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("bitRate")
    private Integer bitRate;

    @JsonProperty("type")
    private String type;

    RadioStationStreamDocument() {
    }

    public RadioStationStreamDocument(String uniqueId,
                                      String radioStationUniqueId,
                                      String url) {
        this.uniqueId = uniqueId;
        this.radioStationUniqueId = radioStationUniqueId;
        this.url = url;
    }

    public String getUniqueId() {
        return uniqueId;
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
}
