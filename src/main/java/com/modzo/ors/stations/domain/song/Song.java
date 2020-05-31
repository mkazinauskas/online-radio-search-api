package com.modzo.ors.stations.domain.song;

import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;
import org.springframework.data.domain.Example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(generator = "songs_sequence", strategy = SEQUENCE)
    @SequenceGenerator(name = "songs_sequence", sequenceName = "songs_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_id", length = 20, unique = true, nullable = false)
    private String uniqueId = randomAlphanumeric(40);

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

    public String getUniqueId() {
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

    public static class ExampleBuilder {

        private final Song song;

        public ExampleBuilder() {
            song = new Song();
            song.uniqueId = null;
            song.created = null;
        }

        public Song.ExampleBuilder withId(Long id) {
            this.song.id = id;
            return this;
        }

        public Example<Song> build() {
            return Example.of(song);
        }
    }
}
