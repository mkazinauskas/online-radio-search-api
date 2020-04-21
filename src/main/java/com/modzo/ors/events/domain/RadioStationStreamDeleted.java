package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_STREAM_DELETED;

public class RadioStationStreamDeleted extends DomainEvent {

    private final Data data;

    public RadioStationStreamDeleted(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final long id;

        private final String uniqueId;

        private final long radioStationId;

        private final String radioStationUniqueId;

        @JsonCreator
        public Data(@JsonProperty("id") long id,
                    @JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("radioStationId") long radioStationId,
                    @JsonProperty("radioStationUniqueId") String radioStationUniqueId
        ) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.radioStationId = radioStationId;
            this.radioStationUniqueId = radioStationUniqueId;
        }

        public long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public long getRadioStationId() {
            return radioStationId;
        }

        public String getRadioStationUniqueId() {
            return radioStationUniqueId;
        }

        public static RadioStationStreamDeleted.Data deserialize(String body) {
            return RadioStationStreamDeleted.Data.deserialize(body, RadioStationStreamDeleted.Data.class);
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_STREAM_DELETED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
