package com.modzo.ors.web.components.common.model;

import com.modzo.ors.web.api.radio.stations.RadioStationResponse;
import com.modzo.ors.web.api.search.radio.station.SearchRadioStationResultResponse;
import com.modzo.ors.web.utils.SeoText;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

public class RadioStationModel {

    private final long id;

    private final String uniqueId;

    private final String title;

    private final String seoTitle;

    private final String website;

    private final List<GenreModel> genres;

    public RadioStationModel(long id, String uniqueId, String title, String website, List<GenreModel> genres) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
        this.seoTitle = SeoText.from(title);
        this.website = website;
        this.genres = genres;
    }

    public RadioStationModel(RadioStationResponse response) {
        this.id = response.getId();
        this.uniqueId = response.getUniqueId();
        this.title = response.getTitle();
        this.seoTitle = SeoText.from(response.getTitle());
        this.website = response.getWebsite();
        this.genres = response.getGenres().stream()
                .map(GenreModel::new)
                .collect(Collectors.toList());
    }

    public RadioStationModel(SearchRadioStationResultResponse response) {
        this.id = response.getId();
        this.uniqueId = response.getUniqueId();
        this.title = response.getTitle();
        this.seoTitle = SeoText.from(response.getTitle());
        this.website = response.getWebsite();
        this.genres = response.getGenres().stream()
                .map(GenreModel::new)
                .collect(Collectors.toList());
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

    public String getSeoTitle() {
        return seoTitle;
    }

    public String getWebsite() {
        return website;
    }

    public List<GenreModel> getGenres() {
        return genres;
    }

    public boolean hasGenres() {
        return !CollectionUtils.isEmpty(genres);
    }
}
