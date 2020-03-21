package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_STREAM_DELETED;
import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_STREAM_URL_DELETED;

public class RadioStationStreamUrlDeleted extends DomainEvent {

    private final Data data;

    public RadioStationStreamUrlDeleted(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final long id;

        private final String uniqueId;

        private final long streamId;

        private final String streamUniqueId;

        @JsonCreator
        public Data(@JsonProperty("id") long id,
                    @JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("streamId") long streamId,
                    @JsonProperty("streamUniqueId") String streamUniqueId
        ) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.streamId = streamId;
            this.streamUniqueId = streamUniqueId;
        }

        public long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public long getStreamId() {
            return streamId;
        }

        public String getStreamUniqueId() {
            return streamUniqueId;
        }

        public static RadioStationStreamUrlDeleted.Data deserialize(String body) {
            return RadioStationStreamUrlDeleted.Data.deserialize(body, RadioStationStreamUrlDeleted.Data.class);
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_STREAM_URL_DELETED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
