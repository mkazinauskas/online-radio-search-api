package com.modzo.ors.events.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "events")
public class Event {

    public enum Type {
        RADIO_STATION_CREATED(RadioStationCreated.Data.class),
        RADIO_STATION_DELETED(RadioStationDeleted.Data.class),
        RADIO_STATION_UPDATED(RadioStationUpdated.Data.class),
        RADIO_STATION_STREAM_URL_CREATED(RadioStationStreamUrlCreated.Data.class),
        RADIO_STATION_STREAM_URL_DELETED(RadioStationStreamUrlDeleted.Data.class),
        RADIO_STATION_STREAM_CHECKED_TIME_UPDATED(RadioStationStreamCheckedTimeUpdated.Data.class),
        RADIO_STATION_STREAM_CREATED(RadioStationStreamCreated.Data.class),
        RADIO_STATION_STREAM_DELETED(RadioStationStreamDeleted.Data.class),
        RADIO_STATION_STREAM_UPDATED(RadioStationStreamUpdated.Data.class),
        STREAM_URL_CHECKED_TIME_UPDATED(StreamUrlCheckedTimeUpdated.Data.class),
        GENRE_CREATED(GenreCreated.Data.class),
        GENRE_DELETED(GenreDeleted.Data.class),
        SONG_CREATED(SongCreated.Data.class),
        SONG_DELETED(SongDeleted.Data.class),
        RADIO_STATION_SONG_CREATED(RadioStationSongCreated.Data.class),
        RADIO_STATION_SONG_DELETED(RadioStationSongDeleted.Data.class);

        private final Class<? extends DomainEvent.Data> eventClass;

        Type(Class<? extends DomainEvent.Data> eventClass) {
            this.eventClass = eventClass;
        }

        public Class<? extends DomainEvent.Data> getEventClass() {
            return eventClass;
        }
    }

    @Id
    @GeneratedValue(generator = "events_sequence", strategy = SEQUENCE)
    @SequenceGenerator(name = "events_sequence", sequenceName = "events_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "entity_unique_id", nullable = false)
    private String entityUniqueId;

    @Column(name = "created", nullable = false)
    private ZonedDateTime created = ZonedDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "body", columnDefinition = "clob", nullable = false)
    private String body;

    Event() {
    }

    @Deprecated
    public Event(Type type, String body) {
        this.type = type;
        this.body = body;
    }

    public Event(Type type, String entityUniqueId, String body) {
        this.type = type;
        this.entityUniqueId = entityUniqueId;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public String getEntityUniqueId() {
        return entityUniqueId;
    }

    public ZonedDateTime getCreated() {
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
