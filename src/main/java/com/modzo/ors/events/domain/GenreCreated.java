package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

import static com.modzo.ors.events.domain.Event.Type.GENRE_CREATED;

public class GenreCreated extends DomainEvent {
    private final Data data;

    public GenreCreated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final long id;

        private final String uniqueId;

        private final ZonedDateTime created;

        private final String title;

        @JsonCreator
        public Data(@JsonProperty("id") long id,
                    @JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("created") ZonedDateTime created,
                    @JsonProperty("title") String title) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.created = created;
            this.title = title;
        }

        public long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public ZonedDateTime getCreated() {
            return created;
        }

        public String getTitle() {
            return title;
        }

        public static GenreCreated.Data deserialize(String body) {
            return GenreCreated.Data.deserialize(body, GenreCreated.Data.class);
        }

    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return GENRE_CREATED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
