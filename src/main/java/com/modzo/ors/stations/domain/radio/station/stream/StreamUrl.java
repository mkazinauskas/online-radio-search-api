package com.modzo.ors.stations.domain.radio.station.stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "stream_urls")
public class StreamUrl {

    public enum Type {
        SONGS,
        INFO
    }

    @Id
    @GeneratedValue(generator = "stream_urls_sequence", strategy = SEQUENCE)
    @SequenceGenerator(
            name = "stream_urls_sequence",
            sequenceName = "stream_urls_sequence",
            allocationSize = 1
    )
    @Column(name = "id")
    private long id;

    @Column(name = "unique_id", unique = true, nullable = false)
    private UUID uniqueId = UUID.randomUUID();

    @Column(name = "created", nullable = false)
    private ZonedDateTime created = ZonedDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "radio_station_stream_id")
    private RadioStationStream stream;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "url")
    private String url;

    @Column(name = "checked")
    private ZonedDateTime checked;

    protected StreamUrl() {
    }

    public StreamUrl(Type type, String url) {
        this.type = type;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public RadioStationStream getStream() {
        return stream;
    }

    public void setStream(RadioStationStream stream) {
        this.stream = stream;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ZonedDateTime getChecked() {
        return checked;
    }

    public void setChecked(ZonedDateTime checked) {
        this.checked = checked;
    }
}

