package com.modzo.ors.web.web.search.radio.station.by.played.song;

import com.modzo.ors.web.web.api.songs.SongsClient;
import com.modzo.ors.web.web.utils.SeoText;
import org.springframework.stereotype.Component;

@Component
class SongService {

    private final SongsClient songsClient;

    public SongService(SongsClient songsClient) {
        this.songsClient = songsClient;
    }

    Data retrieveSong(long songId) {
        var songById = songsClient.getSongById(songId).getContent();
        return new Data(
                songById.getId(),
                songById.getTitle()
        );
    }

    static class Data {

        private final long id;

        private final String title;

        private final String seoTitle;

        public Data(long id, String title) {
            this.id = id;
            this.title = title;
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
}
