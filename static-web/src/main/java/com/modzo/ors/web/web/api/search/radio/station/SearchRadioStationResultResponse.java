package com.modzo.ors.web.web.api.search.radio.station;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchRadioStationResultResponse {

    private final String uniqueId;

    private final String title;

    @JsonCreator
    private SearchRadioStationResultResponse(@JsonProperty("uniqueId") String uniqueId,
                                             @JsonProperty("title") String title) {
        this.uniqueId = uniqueId;
        this.title = title;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return title;
    }
}
