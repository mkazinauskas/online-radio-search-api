package com.modzo.ors.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.modzo.ors.domain.events.Event.Type.RADIO_STATION_DELETED;
import static com.modzo.ors.domain.events.Event.Type.RADIO_STATION_STREAM_DELETED;

public class RadioStationStreamDeleted extends DomainEvent {
    private final Data data;

    public RadioStationStreamDeleted(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final String uniqueId;

        private final String radioStationUniqueId;

        @JsonCreator
        public Data(@JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("radioStationUniqueId") String radioStationUniqueId
        ) {
            this.uniqueId = uniqueId;
            this.radioStationUniqueId = radioStationUniqueId;
        }

        public String getUniqueId() {
            return uniqueId;
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
