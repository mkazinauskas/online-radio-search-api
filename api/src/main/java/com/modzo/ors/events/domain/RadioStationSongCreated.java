package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

import static com.modzo.ors.events.domain.Event.Type.RADIO_STATION_SONG_CREATED;

public class RadioStationSongCreated extends DomainEvent {
    private final Data data;

    public RadioStationSongCreated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final long id;

        private final String uniqueId;

        private final long songId;

        private final String songUniqueId;

        private final long radioStationId;

        private final String radioStationUniqueId;

        private final ZonedDateTime playedTime;

        @JsonCreator
        public Data(@JsonProperty("id") long id,
                    @JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("songId") long songId,
                    @JsonProperty("songUniqueId") String songUniqueId,
                    @JsonProperty("radioStationId") long radioStationId,
                    @JsonProperty("radioStationUniqueId") String radioStationUniqueId,
                    @JsonProperty("playedTime") ZonedDateTime playedTime) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.songId = songId;
            this.songUniqueId = songUniqueId;
            this.radioStationId = radioStationId;
            this.radioStationUniqueId = radioStationUniqueId;
            this.playedTime = playedTime;
        }

        public long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public long getSongId() {
            return songId;
        }

        public String getSongUniqueId() {
            return songUniqueId;
        }

        public long getRadioStationId() {
            return radioStationId;
        }

        public String getRadioStationUniqueId() {
            return radioStationUniqueId;
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
