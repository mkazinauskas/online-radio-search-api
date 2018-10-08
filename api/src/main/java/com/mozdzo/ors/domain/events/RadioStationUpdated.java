package com.mozdzo.ors.domain.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

import static com.mozdzo.ors.domain.events.Event.Type.RADIO_STATION_UPDATED;

public class RadioStationUpdated extends DomainEvent {
    private final Data data;

    public RadioStationUpdated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {
        private final String uniqueId;

        private final String title;

        private final String website;

        private final Set<Genre> genres;

        @JsonCreator
        public Data(@JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("title") String title,
                    @JsonProperty("website") String website,
                    @JsonProperty("genres") Set<Genre> genres) {
            this.uniqueId = uniqueId;
            this.title = title;
            this.website = website;
            this.genres = genres;
        }

        public static class Genre {
            private final String uniqueId;

            private final String title;

            @JsonCreator
            public Genre(@JsonProperty("uniqueId") String uniqueId,
                         @JsonProperty("title") String title) {
                this.uniqueId = uniqueId;
                this.title = title;
            }

            public String getUniqueId() {
                return uniqueId;
            }

            public String getTitle() {
                return title;
            }
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public String getTitle() {
            return title;
        }

        public String getWebsite() {
            return website;
        }

        public Set<Genre> getGenres() {
            return genres;
        }
    }

    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return RADIO_STATION_UPDATED;
    }
}
