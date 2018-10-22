package com.mozdzo.ors.search.parsedevents;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.ZonedDateTime;

@Document(indexName = "parsed_events", type = "parsed_event")
public class ParsedEvent {
    @Id
    private String uniqueId;

    private Long eventId;

    private ZonedDateTime date;

    public ParsedEvent() {
    }

    public ParsedEvent(String uniqueId, Long eventId, ZonedDateTime date) {
        this.uniqueId = uniqueId;
        this.eventId = eventId;
        this.date = date;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
}
