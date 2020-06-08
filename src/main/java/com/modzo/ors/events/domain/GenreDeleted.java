package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.modzo.ors.events.domain.Event.Type.GENRE_DELETED;

public class GenreDeleted extends DomainEvent {

    private final Data data;

    public GenreDeleted(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final long id;

        private final String uniqueId;

        @JsonCreator
        public Data(@JsonProperty("id") long id,
                    @JsonProperty("uniqueId") String uniqueId) {
            this.id = id;
            this.uniqueId = uniqueId;
        }

        public long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public static GenreDeleted.Data deserialize(String body) {
            return GenreDeleted.Data.deserialize(body, GenreDeleted.Data.class);
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return GENRE_DELETED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
