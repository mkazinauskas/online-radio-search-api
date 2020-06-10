package com.modzo.ors.stations.domain.song;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(generator = "songs_sequence", strategy = SEQUENCE)
    @SequenceGenerator(name = "songs_sequence", sequenceName = "songs_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_id", unique = true, nullable = false)
    private UUID uniqueId = UUID.randomUUID();

    @Column(name = "created", nullable = false)
    private ZonedDateTime created = ZonedDateTime.now();

    @Column(name = "title", length = 100)
    private String title;

    Song() {
    }

    public Song(String title) {
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
