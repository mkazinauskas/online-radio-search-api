package com.modzo.ors.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.modzo.ors.domain.events.Event.Type.RADIO_STATION_DELETED;

public class RadioStationDeleted extends DomainEvent {
    private final Data data;

    public RadioStationDeleted(Object source, Data data) {
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
