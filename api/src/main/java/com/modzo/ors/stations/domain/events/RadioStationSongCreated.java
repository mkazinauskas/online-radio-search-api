package com.modzo.ors.stations.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

import static com.modzo.ors.stations.domain.events.Event.Type.RADIO_STATION_SONG_CREATED;

public class RadioStationSongCreated extends DomainEvent {
    private final Data data;

    public RadioStationSongCreated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {
        private final String uniqueId;

        private final String radioStationUniqueId;

        private final String songUniqueId;

        private final ZonedDateTime playedTime;

        @JsonCreator
        public Data(@JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("radioStationUniqueId") String radioStationUniqueId,
                    @JsonProperty("songUniqueId") String songUniqueId,
                    @JsonProperty("playedTime") ZonedDateTime playedTime) {
            this.uniqueId = uniqueId;
            this.radioStationUniqueId = radioStationUniqueId;
            this.songUniqueId = songUniqueId;
            this.playedTime = playedTime;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public String getRadioStationUniqueId() {
            return radioStationUniqueId;
        }

        public String getSongUniqueId() {
            return songUniqueId;
        }

        public ZonedDateTime getPlayedTime() {
            return playedTime;
        }

        public static RadioStationSongCreated.Data deserialize(String body) {
            return RadioStationSongCreated.Data.deserialize(body, RadioStationSongCreated.Data.class);
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_SONG_CREATED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
