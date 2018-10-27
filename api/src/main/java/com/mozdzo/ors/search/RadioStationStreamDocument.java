package com.mozdzo.ors.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "online_radio_search", type = "radio_station_stream")
public class RadioStationStreamDocument {
    @Id
    private String uniqueId;

    private String radioStationUniqueId;

    private String url;

    private Integer bitRate;

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

    public Integer getBitRate() {
        return bitRate;
    }

    public String getType() {
        return type;
    }
}
