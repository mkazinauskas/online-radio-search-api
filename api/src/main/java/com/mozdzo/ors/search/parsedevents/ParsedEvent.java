package com.mozdzo.ors.search.parsedevents;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.ZonedDateTime;

@Document(indexName = "parsed_events", type = "parsed_event")
public class ParsedEvent {
    @Id
    private Long eventId;

    private ZonedDateTime date = ZonedDateTime.now();

    public ParsedEvent() {
    }

    public ParsedEvent(Long eventId) {
        this.eventId = eventId;
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
