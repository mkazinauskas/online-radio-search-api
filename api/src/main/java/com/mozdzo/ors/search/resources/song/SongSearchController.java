package com.mozdzo.ors.search.resources.song;

import com.mozdzo.ors.search.domain.SongDocument;
import com.mozdzo.ors.search.domain.commands.SearchSongsByTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class SongSearchController {

    private final SearchSongsByTitle.Handler searchHandler;

    public SongSearchController(SearchSongsByTitle.Handler searchHandler) {
        this.searchHandler = searchHandler;
    }

    @GetMapping("/songs/search")
    ResponseEntity<SongsResultResource> search(@RequestParam("title") String title,
                                               Pageable pageable) {
        Page<SongDocument> foundSongs = searchHandler.handle(
                new SearchSongsByTitle(title, pageable)
        );
        return ok(SongsResultResource.create(foundSongs, pageable));
    }

}