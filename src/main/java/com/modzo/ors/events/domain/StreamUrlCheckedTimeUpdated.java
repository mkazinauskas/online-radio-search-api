package com.modzo.ors.events.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

import static com.modzo.ors.events.domain.Event.Type.STREAM_URL_CHECKED_TIME_UPDATED;

public class StreamUrlCheckedTimeUpdated extends DomainEvent {
    private final Data data;

    public StreamUrlCheckedTimeUpdated(Object source, Data data) {
        super(source);
        this.data = data;
    }

    public static class Data extends DomainEvent.Data {

        private final long id;

        private final String uniqueId;

        private final ZonedDateTime checkedTime;

        @JsonCreator
        public Data(@JsonProperty("id") long id,
                    @JsonProperty("uniqueId") String uniqueId,
                    @JsonProperty("checkedTime") ZonedDateTime checkedTime) {
            this.id = id;
            this.uniqueId = uniqueId;
            this.checkedTime = checkedTime;
        }

        public long getId() {
            return id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public ZonedDateTime getCheckedTime() {
            return checkedTime;
        }

        public static StreamUrlCheckedTimeUpdated.Data deserialize(String body) {
            return StreamUrlCheckedTimeUpdated.Data.deserialize(
                    body,
                    StreamUrlCheckedTimeUpdated.Data.class
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
