package com.mozdzo.ors.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mozdzo.ors.domain.radio.station.stream.RadioStationStream;

import static com.mozdzo.ors.domain.events.Event.Type.RADIO_STATION_STREAM_UPDATED;

public class RadioStationStreamUpdated extends DomainEvent {
    private final Data data;

    public RadioStationStreamUpdated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {
        private final String uniqueId;

        private final String url;

        private final Integer bitRate;

        private final String type;

        @JsonCreator
        public Data(@JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("url")String url,
                    @JsonProperty("bitRate") Integer bitRate,
                    @JsonProperty("type") String type) {
            this.uniqueId = uniqueId;
            this.url = url;
            this.bitRate = bitRate;
            this.type = type;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public String getUrl() {
            return url;
        }

        public Integer getBitRate() {
            return bitRate;
        }

        public String getType() {
            return type;
        }
    }

    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_STREAM_UPDATED;
    }
}
