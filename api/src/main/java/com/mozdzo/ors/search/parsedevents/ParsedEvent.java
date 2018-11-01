package com.mozdzo.ors.search.parsedevents;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;

@Document(indexName = "parsed_events", type = "parsed_event")
public class ParsedEvent {
    @Id
    @JsonProperty("eventId")
    @Field(type = FieldType.Long)
    private Long id;

    @JsonProperty("date")
    private ZonedDateTime date = ZonedDateTime.now();

    public ParsedEvent() {
    }

    public ParsedEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
}
