package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_SONG_DELETED;

public class RadioStationSongDeleted extends DomainEvent {
    private final Data data;

    public RadioStationSongDeleted(Object source, Data data) {
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

        public static RadioStationSongDeleted.Data deserialize(String body) {
            return RadioStationSongDeleted.Data.deserialize(body, RadioStationSongDeleted.Data.class);
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_SONG_DELETED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
