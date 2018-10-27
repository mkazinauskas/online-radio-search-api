package com.mozdzo.ors.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.mozdzo.ors.domain.events.Event.Type.RADIO_STATION_STREAM_CREATED;

public class RadioStationStreamCreated extends DomainEvent {
    private final Data data;

    public RadioStationStreamCreated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {
        private final String uniqueId;

        private final String url;

        @JsonCreator
        public Data(@JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("url") String url) {
            this.uniqueId = uniqueId;
            this.url = url;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public String getUrl() {
            return url;
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
