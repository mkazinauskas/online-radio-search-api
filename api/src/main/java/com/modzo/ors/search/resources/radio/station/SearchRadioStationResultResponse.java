package com.modzo.ors.search.resources.radio.station;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.search.domain.RadioStationDocument;

public class SearchRadioStationResultResponse {

    private final long id;

    private final String uniqueId;

    private final String title;

    @JsonCreator
    private SearchRadioStationResultResponse(@JsonProperty("id") long id,
                                             @JsonProperty("uniqueId") String uniqueId,
                                             @JsonProperty("title") String title) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
    }

    static SearchRadioStationResultResponse create(RadioStationDocument radioStationDocument) {
        return new SearchRadioStationResultResponse(
                radioStationDocument.getId(),
                radioStationDocument.getUniqueId(),
                radioStationDocument.getTitle()
        );
    }

    public long getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return title;
    }
}
