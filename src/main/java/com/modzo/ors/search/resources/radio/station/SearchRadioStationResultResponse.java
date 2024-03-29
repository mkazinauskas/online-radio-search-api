package com.modzo.ors.search.resources.radio.station;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.RadioStation;

import java.util.UUID;

public class SearchRadioStationResultResponse {

    private final long id;

    private final UUID uniqueId;

    private final String title;

    private final String website;

    @JsonCreator
    private SearchRadioStationResultResponse(@JsonProperty("id") long id,
                                             @JsonProperty("uniqueId") UUID uniqueId,
                                             @JsonProperty("title") String title,
                                             @JsonProperty("website") String website) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
        this.website = website;
    }

    static SearchRadioStationResultResponse create(RadioStation radioStationDocument) {
        return new SearchRadioStationResultResponse(
                radioStationDocument.getId(),
                radioStationDocument.getUniqueId(),
                radioStationDocument.getTitle(),
                radioStationDocument.getWebsite()
        );
    }

    public long getId() {
        return id;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

}
