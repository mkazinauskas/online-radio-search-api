package com.modzo.ors.stations.domain.song.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.stations.domain.song.Song;
import com.modzo.ors.stations.domain.song.Songs;
import com.modzo.ors.stations.events.StationsDomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

public class DeleteSong {

    private final long id;

    public DeleteSong(long id) {
        this.id = id;
    }

    @Component
    public static class Handler {

        private final Songs songs;

        private final Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        Handler(Songs songs,
                Validator validator,
                ApplicationEventPublisher applicationEventPublisher) {
            this.songs = songs;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @Transactional
        public void handle(DeleteSong command) {
            validator.validate(command);

            Song song = this.songs.findById(command.id).get();

            songs.delete(song);

            applicationEventPublisher.publishEvent(
                    new StationsDomainEvent(
                            song,
                            StationsDomainEvent.Action.DELETED,
                            StationsDomainEvent.Type.SONG,
                            song.getId()
                    )
            );
        }
    }

    @Component
    private static class Validator {
        private final Songs songs;

        Validator(Songs songs) {
            this.songs = songs;
        }

        void validate(DeleteSong command) {
            songs.findById(command.id)
                    .orElseThrow(() -> songWithIdDoesNotExist(command));

            if (command.id <= 0) {
                throw new DomainException(
                        "FIELD_ID_SHOULD_BE_POSITIVE",
                        "id",
                        "Field id must be positive"
                );
            }
        }

        private DomainException songWithIdDoesNotExist(DeleteSong command) {
            return new DomainException(
                    "SONG_WITH_ID_DOES_NOT_EXIST",
                    "id",
                    String.format("Song with id = `%s` does not exist", command.id)
            );
        }
    }
}
