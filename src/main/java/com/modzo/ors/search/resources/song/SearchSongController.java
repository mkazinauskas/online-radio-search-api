package com.modzo.ors.search.resources.song;

import com.modzo.ors.search.domain.commands.SearchSongsByTitle;
import com.modzo.ors.stations.domain.song.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class SearchSongController {

    private final SearchSongsByTitle.Handler searchHandler;

    public SearchSongController(SearchSongsByTitle.Handler searchHandler) {
        this.searchHandler = searchHandler;
    }

    @GetMapping(value = "/search/song", params = {"title"})
    ResponseEntity<SearchSongResultsModel> search(@RequestParam("title") String title, Pageable pageable) {
        Page<Song> foundSongs = searchHandler.handle(
                new SearchSongsByTitle(title, pageable)
        );

        return ok(SearchSongResultsModel.create(foundSongs, pageable, title));
    }
}