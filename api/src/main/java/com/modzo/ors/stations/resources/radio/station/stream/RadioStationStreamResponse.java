package com.modzo.ors.stations.resources.radio.station.stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.stream.RadioStationStream;

public class RadioStationStreamResponse {

    private final long id;

    private final String uniqueId;

    private final String url;

    private final int bitRate;

    private final String type;

    @JsonCreator
    private RadioStationStreamResponse(@JsonProperty("id") long id,
                                       @JsonProperty("uniqueId") String uniqueId,
                                       @JsonProperty("url") String url,
                                       @JsonProperty("bitRate") Integer bitRate,
                                       @JsonProperty("type")  String type) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.url = url;
        this.bitRate = bitRate;
        this.type = type;
    }

    static RadioStationStreamResponse create(RadioStationStream radioStationStream) {
        return new RadioStationStreamResponse(
                radioStationStream.getId(),
                radioStationStream.getUniqueId(),
                radioStationStream.getUrl(),
                radioStationStream.getBitRate(),
                radioStationStream.getType().name()
        );
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
