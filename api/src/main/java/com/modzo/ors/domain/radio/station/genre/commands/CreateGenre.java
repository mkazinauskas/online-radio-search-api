package com.modzo.ors.domain.radio.station.genre.commands;

import com.modzo.ors.domain.DomainException;
import com.modzo.ors.domain.events.GenreCreated;
import com.modzo.ors.domain.radio.station.genre.Genre;
import com.modzo.ors.domain.radio.station.genre.Genres;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CreateGenre {

    private final String title;

    public CreateGenre(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private Genre toGenre() {
        return new Genre(this.title);
    }

    @Component
    public static class Handler {
        private final Genres genres;

        private final Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        public Handler(Genres genres,
                       Validator validator,
                       ApplicationEventPublisher applicationEventPublisher) {
            this.genres = genres;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @Transactional
        public Result handle(CreateGenre command) {
            validator.validate(command);
            Genre genre = genres.save(command.toGenre());
            applicationEventPublisher.publishEvent(
                    new GenreCreated(genre,
                            new GenreCreated.Data(genre.getUniqueId(),
                                    genre.getTitle()))
            );
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
