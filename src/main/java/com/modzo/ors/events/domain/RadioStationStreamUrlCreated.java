package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.modzo.ors.stations.domain.radio.station.stream.StreamUrl;

import java.time.ZonedDateTime;

import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_STREAM_URL_CREATED;

public class RadioStationStreamUrlCreated extends DomainEvent {

    private final Data data;

    public RadioStationStreamUrlCreated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final long id;

        private final String uniqueId;

        private final ZonedDateTime created;

        private final long streamId;

        private final String streamUniqueId;

        private final String url;

        private final StreamUrl.Type type;

        public Data(@JsonProperty("id") long id,
                    @JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("created") ZonedDateTime created,
                    @JsonProperty("streamId") long streamId,
                    @JsonProperty("streamUniqueId") String streamUniqueId,
                    @JsonProperty("url") String url,
                    @JsonProperty("type") StreamUrl.Type type) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.created = created;
            this.streamId = streamId;
            this.streamUniqueId = streamUniqueId;
            this.url = url;
            this.type = type;
        }

        public long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public ZonedDateTime getCreated() {
            return created;
        }

        public long getStreamId() {
            return streamId;
        }

        public String getStreamUniqueId() {
            return streamUniqueId;
        }

        public String getUrl() {
            return url;
        }

        public StreamUrl.Type getType() {
            return type;
        }

        public static RadioStationStreamUrlCreated.Data deserialize(String body) {
            return RadioStationStreamUrlCreated.Data.deserialize(body, RadioStationStreamUrlCreated.Data.class);
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_STREAM_URL_CREATED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
