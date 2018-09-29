package com.mozdzo.ors.domain.radio.station.song;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
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

    @Column(name = "radio_station_id")
    private long radioStationId;

    @Column(name = "unique_id", length = 20, unique = true, nullable = false)
    private String uniqueId = randomAlphanumeric(40);


    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "playing_time")
    private ZonedDateTime playingTime;

    Song() {
    }

    public Song(long radioStationId, String title, ZonedDateTime playingTime) {
        this.radioStationId = radioStationId;
        this.title = title;
        this.playingTime = playingTime;
    }

    public Long getId() {
        return id;
    }

    public long getRadioStationId() {
        return radioStationId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(ZonedDateTime playingTime) {
        this.playingTime = playingTime;
    }
}
