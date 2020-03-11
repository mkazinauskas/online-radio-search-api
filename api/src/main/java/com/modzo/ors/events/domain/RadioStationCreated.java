package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_CREATED;

public class RadioStationCreated extends DomainEvent {
    private final Data data;

    public RadioStationCreated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final long id;

        private final String uniqueId;

        private final ZonedDateTime created;

        private final String title;

        private final boolean enabled;

        @JsonCreator
        public Data(@JsonProperty("id") long id,
                    @JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("created") ZonedDateTime created,
                    @JsonProperty("title") String title,
                    @JsonProperty("enabled") boolean enabled
        ) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.created = created;
            this.title = title;
            this.enabled = enabled;
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

        public String getTitle() {
            return title;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public static RadioStationCreated.Data deserialize(String body) {
            return RadioStationCreated.Data.deserialize(body, RadioStationCreated.Data.class);
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_CREATED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
