package com.modzo.ors.stations.domain.radio.station.genre.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import com.modzo.ors.stations.domain.radio.station.genre.Genres;
import com.modzo.ors.stations.events.StationsDomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

public class DeleteGenre {

    private final long id;

    public DeleteGenre(long id) {
        this.id = id;
    }

    @Component
    public static class Handler {

        private final Genres genres;

        private final Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        Handler(Genres genres,
                Validator validator,
                ApplicationEventPublisher applicationEventPublisher) {
            this.genres = genres;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @Transactional
        public void handle(DeleteGenre command) {

            validator.validate(command);

            Genre genre = genres.findById(command.id).get();

            genres.delete(genre);

            applicationEventPublisher.publishEvent(
                    new StationsDomainEvent(
                            genre,
                            StationsDomainEvent.Action.DELETED,
                            StationsDomainEvent.Type.GENRE,
                            genre.getId()
                    )
            );
        }
    }

    @Component
    private static class Validator {

        private final Genres genres;

        public Validator(Genres genres) {
            this.genres = genres;
        }

        void validate(DeleteGenre command) {
            if (command.id <= 0) {
                throw new DomainException(
                        "FIELD_ID_SHOULD_BE_POSITIVE",
                        "id",
                        "Field id must be positive"
                );
            }

            genres.findById(command.id)
                    .orElseThrow(() -> genreWithIdDoesNotExist(command));

        }

        private DomainException genreWithIdDoesNotExist(DeleteGenre command) {
            return new DomainException(
                    "GENRE_WITH_ID_DOES_NOT_EXIST",
                    "id",
                    String.format("Genre with id = `%s` does not exist", command.id)
            );
        }
    }
}
