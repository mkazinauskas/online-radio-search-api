package com.modzo.ors.web.web.search.song;

import com.modzo.ors.web.web.api.search.song.SearchSongClient;
import com.modzo.ors.web.web.api.search.song.SearchSongResultResponse;
import com.modzo.ors.web.web.utils.SeoText;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
class SearchBySongService {

    private final SearchSongClient searchSongClient;

    SearchBySongService(SearchSongClient searchSongClient) {
        this.searchSongClient = searchSongClient;
    }

    Data retrieve(String seoTitle) {
        var title = SeoText.revert(seoTitle);
        PagedModel<SearchSongResultResponse> foundSongs = this.searchSongClient.searchSongsByTitle(title);
        Collection<SearchSongResultResponse> content = foundSongs.getContent();
        List<Data.Song> convertedSongs = content.stream()
                .map(song -> new Data.Song(song.getId(), song.getUniqueId(), song.getTitle()))
                .collect(Collectors.toList());

        return new Data(convertedSongs);

    }

    static class Data {

        private final List<Song> songs;

        public Data(List<Song> songs) {
            this.songs = songs;
        }

        static class Song {

            private final long id;

            private final String uniqueId;

            private final String title;

            public Song(long id, String uniqueId, String title) {
                this.id = id;
                this.uniqueId = uniqueId;
                this.title = title;
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

        public List<Song> getSongs() {
            return songs;
        }
    }
}
