package com.mozdzo.ors.domain.radio.station.genre.commands;

import com.mozdzo.ors.domain.DomainException;
import com.mozdzo.ors.domain.radio.station.genre.Genre;
import com.mozdzo.ors.domain.radio.station.genre.Genres;
import org.springframework.stereotype.Component;

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

        public Optional<Genre> handle(FindGenre command) {
            validator.validate(command);
            return genres.findByTitle(command.title);
        }
    }

    @Component
    private static class Validator {
        void validate(FindGenre command) {
            if (isBlank(command.title)) {
                throw new DomainException("FIELD_GENRE_TITLE_CANNOT_BE_BLANK",
                        "Genre title cannot be blank");
            }
        }
    }
}
