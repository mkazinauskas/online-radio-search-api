package com.modzo.ors.web.web.search.genre.radio.stations;

import com.modzo.ors.web.web.api.genres.GenresClient;
import com.modzo.ors.web.web.components.common.model.GenreModel;
import com.modzo.ors.web.web.components.common.model.SongModel;
import org.springframework.stereotype.Component;

@Component
class GenreService {

    private final GenresClient genresClient;

    public GenreService(GenresClient genresClient) {
        this.genresClient = genresClient;
    }

    GenreModel retrieveGenre(long genreId) {
        var content = genresClient.getByGenreId(genreId).getContent();
        return new GenreModel(content);
    }
}
