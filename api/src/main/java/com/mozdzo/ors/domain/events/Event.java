package com.mozdzo.ors.domain.events;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(generator = "events_sequence", strategy = SEQUENCE)
    @SequenceGenerator(name = "events_sequence", sequenceName = "events_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "created", nullable = false)
    private LocalDateTime created = now();

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "body", columnDefinition = "clob", nullable = false)
    private String body;

    public enum Type {
        RADIO_STATION_CREATED(RadioStationCreated.class);

        private final Class eventClass;

        Type(Class eventClass) {
            this.eventClass = eventClass;
        }

        public Class getEventClass() {
            return eventClass;
        }
    }

    Event() {
    }

    public Event(Type type, String body) {
        this.type = type;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
