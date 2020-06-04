package com.modzo.ors.stations.domain.song.commands;

import com.modzo.ors.stations.domain.DomainException;
import com.modzo.ors.events.domain.SongCreated;
import com.modzo.ors.stations.domain.radio.station.RadioStations;
import com.modzo.ors.stations.domain.song.Song;
import com.modzo.ors.stations.domain.song.Songs;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class CreateSong {
    private final String title;

    public CreateSong(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private Song toSong() {
        return new Song(title);
    }

    @Component
    public static class Handler {
        private final Songs songs;

        private final Validator validator;

        private final ApplicationEventPublisher applicationEventPublisher;

        public Handler(Songs songs, Validator validator, ApplicationEventPublisher applicationEventPublisher) {
            this.songs = songs;
            this.validator = validator;
            this.applicationEventPublisher = applicationEventPublisher;
        }

        @Transactional
        public Result handle(CreateSong command) {
            validator.validate(command);
            Song song = songs.save(command.toSong());
            applicationEventPublisher.publishEvent(
                    new SongCreated(
                            song,
                            new SongCreated.Data(
                                    song.getId(),
                                    song.getUniqueId(),
                                    song.getCreated(),
                                    song.getTitle()
                            ))
            );
            return new Result(song.getId());
        }
    }

    @Component
    private static class Validator {

        private final Songs songs;

        public Validator(Songs songs) {
            this.songs = songs;
        }

        void validate(CreateSong command) {
            if (isBlank(command.title)) {
                throw new DomainException("FIELD_TITLE_NOT_BLANK", "Field title cannot be blank");
            }

            if(songs.findByTitle(command.title).isPresent()){
                throw new DomainException(
                        "FIELD_TITLE_ALREADY_EXISTS",
                        String.format("Title `%s` already exists in database", command.title)
                );
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
