package com.modzo.ors.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.modzo.ors.domain.events.Event.Type.SONG_DELETED;

public class SongDeleted extends DomainEvent {
    private final Data data;

    public SongDeleted(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {
        private final String uniqueId;


        @JsonCreator
        public Data(@JsonProperty("uniqueId") String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public static SongDeleted.Data deserialize(String body) {
            return SongDeleted.Data.deserialize(body, SongDeleted.Data.class);
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return SONG_DELETED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
