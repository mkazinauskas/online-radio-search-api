package com.modzo.ors.stations.resources.genre;

import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import com.modzo.ors.stations.domain.radio.station.genre.commands.GetGenre;
import com.modzo.ors.stations.domain.radio.station.genre.commands.GetGenres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
class GenreController {

    private final GetGenre.Handler getGenreHandler;

    private final GetGenres.Handler getGenresHandler;

    GenreController(GetGenre.Handler getGenreHandler, GetGenres.Handler getGenresHandler) {
        this.getGenreHandler = getGenreHandler;
        this.getGenresHandler = getGenresHandler;
    }

    @GetMapping("/genres")
    ResponseEntity<GenresModel> getGenres(Pageable pageable) {
        Page<Genre> foundGenres = getGenresHandler.handle(new GetGenres(pageable));
        return ok(GenresModel.create(foundGenres, pageable));
    }

    @GetMapping("/genres/{id}")
    ResponseEntity<GenreModel> getGenre(@PathVariable("id") long id) {
        Genre foundGenre = getGenreHandler.handle(new GetGenre(id));
        return ok(GenreModel.create(foundGenre));
    }
}