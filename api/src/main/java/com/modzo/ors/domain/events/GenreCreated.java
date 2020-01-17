package com.modzo.ors.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.modzo.ors.domain.events.Event.Type.GENRE_CREATED;

public class GenreCreated extends DomainEvent {
    private final Data data;

    public GenreCreated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {
        private final String uniqueId;

        private final String title;

        @JsonCreator
        public Data(@JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("title") String title) {
            this.uniqueId = uniqueId;
            this.title = title;
        }

        public static class Genre {
            private final String uniqueId;

            private final String title;

            @JsonCreator
            public Genre(@JsonProperty("uniqueId") String uniqueId,
                         @JsonProperty("title") String title) {
                this.uniqueId = uniqueId;
                this.title = title;
            }

            public String getUniqueId() {
                return uniqueId;
            }

            public String getTitle() {
                return title;
            }
        }

        public String getUniqueId() {
            return uniqueId;
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
