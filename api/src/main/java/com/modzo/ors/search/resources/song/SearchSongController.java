package com.modzo.ors.search.resources.song;

import com.modzo.ors.last.searches.domain.commands.CreateSearchedQuery;
import com.modzo.ors.search.domain.SongDocument;
import com.modzo.ors.search.domain.commands.SearchSongsByTitle;
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

    private final CreateSearchedQuery.Handler lastSearchedQueryHandler;

    public SearchSongController(SearchSongsByTitle.Handler searchHandler,
                                CreateSearchedQuery.Handler lastSearchedQueryHandler) {
        this.searchHandler = searchHandler;
        this.lastSearchedQueryHandler = lastSearchedQueryHandler;
    }

    @GetMapping(value = "/search/song", params = {"title"})
    ResponseEntity<SearchSongResultsModel> search(@RequestParam("title") String title, Pageable pageable) {
        Page<SongDocument> foundSongs = searchHandler.handle(
                new SearchSongsByTitle(title, pageable)
        );
        lastSearchedQueryHandler.handle(new CreateSearchedQuery(title));
        return ok(SearchSongResultsModel.create(foundSongs, pageable, title));
    }
}