package com.modzo.ors.stations.domain.radio.station.genre.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import com.modzo.ors.stations.domain.radio.station.genre.Genres;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class FindGenre {

    private final String title;

    public FindGenre(String title) {
        this.title = title;
    }

    @Component
    public static class Handler {
        private final Genres genres;

        private final Validator validator;

        Handler(Genres genres, Validator validator) {
            this.genres = genres;
            this.validator = validator;
        }

        @Transactional(readOnly = true)
        public Optional<Genre> handle(FindGenre command) {
            validator.validate(command);
            return genres.findByTitle(command.title);
        }
    }

    @Component
    private static class Validator {
        void validate(FindGenre command) {
            if (isBlank(command.title)) {
                throw new DomainException(
                        "FIELD_GENRE_TITLE_CANNOT_BE_BLANK",
                        "title",
                        "Genre title cannot be blank"
                );
            }
        }
    }
}
