package com.mozdzo.ors.domain.radio.station.genre.commands;

import com.mozdzo.ors.domain.DomainException;
import com.mozdzo.ors.domain.radio.station.genre.Genre;
import com.mozdzo.ors.domain.radio.station.genre.Genres;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CreateGenre {

    private final String title;

    public CreateGenre(String title) {
        this.title = title;
    }

    private Genre toGenre() {
        return new Genre(this.title);
    }

    @Component
    public static class Handler {
        private final Genres genres;

        private final Validator validator;

        Handler(Genres genres, Validator validator) {
            this.genres = genres;
            this.validator = validator;
        }

        public Result handle(CreateGenre command) {
            validator.validate(command);
            Genre genre = genres.save(command.toGenre());
            return new Result(genre.getId());
        }
    }

    @Component
    private static class Validator {

        void validate(CreateGenre command) {
            if (isBlank(command.title)) {
                throw new DomainException("FIELD_TITLE_NOT_BLANK", "Field title cannot be blank");
            }
        }
    }

    public static class Result {
        public final long id;

        Result(long id) {
            this.id = id;
        }
    }
}
