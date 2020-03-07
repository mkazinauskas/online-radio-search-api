package com.modzo.ors.web.web.components.common.model;

import com.modzo.ors.web.web.api.radio.stations.streams.RadioStationStreamResponse;

public class RadioStationStreamModel {

    private final long id;

    private final String uniqueId;

    private final String url;

    private final Integer bitRate;

    private final String type;

    public RadioStationStreamModel(RadioStationStreamResponse response) {
        this.id = response.getId();
        this.uniqueId = response.getUniqueId();
        this.url = response.getUrl();
        this.bitRate = response.getBitRate();
        this.type = response.getType();
    }

    public long getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getUrl() {
        return url;
    }

    public int getBitRate() {
        return bitRate;
    }

    public String getType() {
        return type;
    }
}
