package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_STREAM_INFO_CHECKED_UPDATED;

public class RadioStationStreamInfoCheckedUpdated extends DomainEvent {

    private final Data data;

    public RadioStationStreamInfoCheckedUpdated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final long id;

        private final String uniqueId;

        private final long radioStationId;

        private final String radioStationUniqueId;

        private final ZonedDateTime infoChecked;

        @JsonCreator
        public Data(@JsonProperty("id") long id,
                    @JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("radioStationId") long radioStationId,
                    @JsonProperty("radioStationUniqueId") String radioStationUniqueId,
                    @JsonProperty("infoChecked") ZonedDateTime infoChecked) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.radioStationId = radioStationId;
            this.radioStationUniqueId = radioStationUniqueId;
            this.infoChecked = infoChecked;
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

        public ZonedDateTime getInfoChecked() {
            return infoChecked;
        }

        public static RadioStationStreamInfoCheckedUpdated.Data deserialize(String body) {
            return RadioStationStreamInfoCheckedUpdated.Data.deserialize(
                    body,
                    RadioStationStreamInfoCheckedUpdated.Data.class
            );
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_STREAM_INFO_CHECKED_UPDATED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
