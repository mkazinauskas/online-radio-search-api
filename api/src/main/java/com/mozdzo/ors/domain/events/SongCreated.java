package com.mozdzo.ors.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.mozdzo.ors.domain.events.Event.Type.SONG_CREATED;

public class SongCreated extends DomainEvent {
    private final Data data;

    public SongCreated(Object source, Data data) {
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

        public String getUniqueId() {
            return uniqueId;
        }

        public String getTitle() {
            return title;
        }

        public static SongCreated.Data deserialize(String body) {
            return SongCreated.Data.deserialize(body, SongCreated.Data.class);
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return SONG_CREATED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
