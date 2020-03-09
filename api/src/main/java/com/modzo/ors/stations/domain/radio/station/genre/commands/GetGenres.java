package com.modzo.ors.stations.domain.radio.station.genre.commands;

import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import com.modzo.ors.stations.domain.radio.station.genre.Genres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.requireNonNull;

public class GetGenres {

    private final Pageable pageable;

    public GetGenres(Pageable pageable) {
        this.pageable = requireNonNull(pageable);
    }

    @Component
    public static class Handler {

        private final Genres genres;


        public Handler(Genres genres) {
            this.genres = genres;
        }

        @Transactional(readOnly = true)
        public Page<Genre> handle(GetGenres command) {
            return genres.findAll(command.pageable);
        }
    }
}
