package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_STREAM_CREATED;

public class RadioStationStreamCreated extends DomainEvent {
    private final Data data;

    public RadioStationStreamCreated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final long id;

        private final String uniqueId;

        private final long radioStationId;

        private final String radioStationUniqueId;

        private final String url;

        @JsonCreator
        public Data(@JsonProperty("id") long id,
                    @JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("radioStationId") long radioStationId,
                    @JsonProperty("radioStationUniqueId") String radioStationUniqueId,
                    @JsonProperty("url") String url) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.radioStationId = radioStationId;
            this.radioStationUniqueId = radioStationUniqueId;
            this.url = url;
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

        public String getUrl() {
            return url;
        }

        public static RadioStationStreamCreated.Data deserialize(String body) {
            return RadioStationStreamCreated.Data.deserialize(body, RadioStationStreamCreated.Data.class);
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_STREAM_CREATED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
