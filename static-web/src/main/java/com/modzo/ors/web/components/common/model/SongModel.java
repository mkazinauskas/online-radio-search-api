package com.modzo.ors.web.components.common.model;

import com.modzo.ors.web.api.search.song.SearchSongResultResponse;
import com.modzo.ors.web.api.songs.SongResponse;
import com.modzo.ors.web.utils.SeoText;

public class SongModel {

    private final long id;

    private final String title;

    private final String seoTitle;

    public SongModel(long id, String title) {
        this.id = id;
        this.title = title;
        this.seoTitle = SeoText.from(title);
    }

    public SongModel(SongResponse response) {
        this.id = response.getId();
        this.title = response.getTitle();
        this.seoTitle = SeoText.from(title);
    }

    public SongModel(SearchSongResultResponse response) {
        this.id = response.getId();
        this.title = response.getTitle();
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
