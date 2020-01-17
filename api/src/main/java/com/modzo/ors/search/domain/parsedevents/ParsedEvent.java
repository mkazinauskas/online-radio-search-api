package com.modzo.ors.search.domain.parsedevents;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.ZonedDateTime;

import static java.lang.String.valueOf;

@Document(indexName = "parsed_events", type = "parsed_event")
public class ParsedEvent {
    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("eventId")
    private Long eventId;

    @JsonProperty("date")
    private ZonedDateTime date = ZonedDateTime.now();

    ParsedEvent() {
    }

    public ParsedEvent(Long id) {
        this.id = valueOf(id);
        this.eventId = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
