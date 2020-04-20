package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_DELETED;

public class RadioStationDeleted extends DomainEvent {
    private final Data data;

    public RadioStationDeleted(Object source, Data data) {
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

        public static RadioStationDeleted.Data deserialize(String body) {
            return RadioStationDeleted.Data.deserialize(body, RadioStationDeleted.Data.class);
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_DELETED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
