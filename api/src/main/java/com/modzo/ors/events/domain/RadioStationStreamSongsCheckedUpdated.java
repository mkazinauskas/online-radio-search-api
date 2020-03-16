package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_STREAM_SONGS_CHECKED_UPDATED;

public class RadioStationStreamSongsCheckedUpdated extends DomainEvent {
    private final Data data;

    public RadioStationStreamSongsCheckedUpdated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final long id;

        private final String uniqueId;

        private final ZonedDateTime songsChecked;

        @JsonCreator
        public Data(@JsonProperty("id") long id,
                    @JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("songsChecked") ZonedDateTime songsChecked) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.songsChecked = songsChecked;
        }

        public long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public ZonedDateTime getSongsChecked() {
            return songsChecked;
        }

        public static RadioStationStreamSongsCheckedUpdated.Data deserialize(String body) {
            return RadioStationStreamSongsCheckedUpdated.Data.deserialize(
                    body,
                    RadioStationStreamSongsCheckedUpdated.Data.class
            );
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_STREAM_SONGS_CHECKED_UPDATED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
