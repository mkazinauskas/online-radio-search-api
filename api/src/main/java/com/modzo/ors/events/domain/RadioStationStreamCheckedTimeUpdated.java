package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

import static com.modzo.ors.events.domain.Event.Type.STREAM_URL_CHECKED_TIME_UPDATED;

public class RadioStationStreamCheckedTimeUpdated extends DomainEvent {

    private final Data data;

    public RadioStationStreamCheckedTimeUpdated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final long streamId;

        private final String uniqueId;

        private final ZonedDateTime checkedTime;

        @JsonCreator
        public Data(@JsonProperty("streamId") long streamId,
                    @JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("checkedTime") ZonedDateTime checkedTime) {
            this.streamId = streamId;
            this.uniqueId = uniqueId;
            this.checkedTime = checkedTime;
        }

        public long getStreamId() {
            return streamId;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public ZonedDateTime getCheckedTime() {
            return checkedTime;
        }

        public static RadioStationStreamCheckedTimeUpdated.Data deserialize(String body) {
            return RadioStationStreamCheckedTimeUpdated.Data.deserialize(
                    body,
                    RadioStationStreamCheckedTimeUpdated.Data.class
            );
        }
    }

    @Override
    Data getData() {
        return this.data;
    }

    @Override
    Event.Type type() {
        return STREAM_URL_CHECKED_TIME_UPDATED;
    }

    @Override
    String uniqueId() {
        return this.data.uniqueId;
    }
}
