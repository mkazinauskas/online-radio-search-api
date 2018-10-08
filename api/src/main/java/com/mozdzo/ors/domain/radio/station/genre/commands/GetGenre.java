package com.mozdzo.ors.domain.radio.station.genre.commands;

import com.mozdzo.ors.domain.DomainException;
import com.mozdzo.ors.domain.radio.station.genre.Genre;
import com.mozdzo.ors.domain.radio.station.genre.Genres;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class GetGenre {
    private final long id;

    public GetGenre(long id) {
        this.id = id;
    }

    @Component
    public static class Handler {
        private final Genres genres;

        public Handler(Genres genres) {
            this.genres = genres;
        }

        @Transactional(readOnly = true)
        public Genre handle(GetGenre command) {
            return genres.findById(command.id)
                    .orElseThrow(() -> new DomainException(
                    "GENRE_BY_ID_NOT_FOUND",
                    "Genre by id was not found")
            );
        }
    }
}
