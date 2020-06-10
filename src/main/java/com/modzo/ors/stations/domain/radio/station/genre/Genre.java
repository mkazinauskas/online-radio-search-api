package com.modzo.ors.stations.domain.radio.station.genre;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(generator = "genres_sequence", strategy = SEQUENCE)
    @SequenceGenerator(name = "genres_sequence", sequenceName = "genres_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_id", unique = true, nullable = false)
    private UUID uniqueId = UUID.randomUUID();

    @Column(name = "created", nullable = false)
    private ZonedDateTime created = ZonedDateTime.now();

    @Column(name = "title", length = 100, unique = true, nullable = false)
    private String title;

    Genre() {
    }

    public Genre(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
