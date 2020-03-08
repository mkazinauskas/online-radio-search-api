package com.modzo.ors.web.web.components.common.model;

import com.modzo.ors.web.web.api.radio.stations.RadioStationResponse;
import com.modzo.ors.web.web.api.search.radio.station.SearchRadioStationResultResponse;

public class GenreModel {

    private final long id;

    private final String uniqueId;

    private final String title;

    public GenreModel(long id, String uniqueId, String title) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
    }

    public GenreModel(SearchRadioStationResultResponse.GenreResponse genreResponse) {
        this.id = genreResponse.getId();
        this.uniqueId = genreResponse.getUniqueId();
        this.title = genreResponse.getTitle();
    }

    public GenreModel(RadioStationResponse.GenreResponse genreResponse) {
        this.id = genreResponse.getId();
        this.uniqueId = genreResponse.getUniqueId();
        this.title = genreResponse.getTitle();
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
