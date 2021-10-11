package com.modzo.ors.search.resources.genre;

import com.modzo.ors.search.domain.commands.SearchGenreByTitle;
import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class SearchGenreController {

    private final SearchGenreByTitle.Handler searchHandler;

    public SearchGenreController(SearchGenreByTitle.Handler searchHandler) {
        this.searchHandler = searchHandler;
    }

    @GetMapping(value = "/search/genre", params = {"title"})
    ResponseEntity<SearchGenreResultsModel> search(@RequestParam("title") String title, Pageable pageable) {
        Page<Genre> foundGenres = searchHandler.handle(
                new SearchGenreByTitle(title, pageable)
        );
        return ok(SearchGenreResultsModel.create(foundGenres, pageable, title));
    }
}