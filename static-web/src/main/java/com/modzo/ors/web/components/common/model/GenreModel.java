package com.modzo.ors.web.components.common.model;

import com.modzo.ors.web.api.genres.GenreResponse;
import com.modzo.ors.web.api.radio.stations.RadioStationResponse;
import com.modzo.ors.web.api.search.genre.SearchGenreResultResponse;
import com.modzo.ors.web.api.search.radio.station.SearchRadioStationResultResponse;
import com.modzo.ors.web.utils.SeoText;

public class GenreModel {

    private final long id;

    private final String title;

    private final String seoTitle;

    public GenreModel(SearchRadioStationResultResponse.GenreResponse genreResponse) {
        this.id = genreResponse.getId();
        this.title = genreResponse.getTitle();
        this.seoTitle = SeoText.from(title);
    }

    public GenreModel(RadioStationResponse.GenreResponse genreResponse) {
        this.id = genreResponse.getId();
        this.title = genreResponse.getTitle();
        this.seoTitle = SeoText.from(title);
    }

    public GenreModel(SearchGenreResultResponse genreResponse) {
        this.id = genreResponse.getId();
        this.title = genreResponse.getTitle();
        this.seoTitle = SeoText.from(title);
    }

    public GenreModel(GenreResponse genreResponse) {
        this.id = genreResponse.getId();
        this.title = genreResponse.getTitle();
        this.seoTitle = SeoText.from(title);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSeoTitle() {
        return seoTitle;
    }
}
