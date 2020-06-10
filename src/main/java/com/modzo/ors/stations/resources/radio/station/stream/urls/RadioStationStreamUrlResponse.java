package com.modzo.ors.stations.resources.radio.station.stream.urls;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;

import java.time.ZonedDateTime;
import java.util.UUID;

public class RadioStationStreamUrlResponse {

    private final long id;

    private final UUID uniqueId;

    private final ZonedDateTime created;

    private final StreamUrl.Type type;

    private final String url;

    private final ZonedDateTime checked;

    @JsonCreator
    private RadioStationStreamUrlResponse(@JsonProperty("id") long id,
                                          @JsonProperty("uniqueId") UUID uniqueId,
                                          @JsonProperty("created") ZonedDateTime created,
                                          @JsonProperty("type") StreamUrl.Type type,
                                          @JsonProperty("url") String url,
                                          @JsonProperty("checked") ZonedDateTime checked) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.created = created;
        this.type = type;
        this.url = url;
        this.checked = checked;
    }

    static RadioStationStreamUrlResponse create(StreamUrl url) {
        return new RadioStationStreamUrlResponse(
                url.getId(),
                url.getUniqueId(),
                url.getCreated(),
                url.getType(),
                url.getUrl(),
                url.getChecked()
        );
    }

    public long getId() {
        return id;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public StreamUrl.Type getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public ZonedDateTime getChecked() {
        return checked;
    }
}
