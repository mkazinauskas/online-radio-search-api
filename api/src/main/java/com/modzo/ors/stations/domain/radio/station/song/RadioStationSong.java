package com.modzo.ors.stations.domain.radio.station.song;

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
@Table(name = "radio_station_songs")
public class RadioStationSong {

    @Id
    @GeneratedValue(generator = "radio_station_songs_sequence", strategy = SEQUENCE)
    @SequenceGenerator(
            name = "radio_station_songs_sequence",
            sequenceName = "radio_station_songs_sequence",
            allocationSize = 1
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_id", length = 40, unique = true, nullable = false)
    private String uniqueId = randomAlphanumeric(40);

    @Column(name = "radio_station_id")
    private long radioStationId;

    @Column(name = "song_id")
    private long songId;

    @Column(name = "playing_time")
    private ZonedDateTime playedTime;

    RadioStationSong() {
    }

    public RadioStationSong(long radioStationId, long songId, ZonedDateTime playedTime) {
        this.radioStationId = radioStationId;
        this.songId = songId;
        this.playedTime = playedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public long getRadioStationId() {
        return radioStationId;
    }

    public void setRadioStationId(long radioStationId) {
        this.radioStationId = radioStationId;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public ZonedDateTime getPlayedTime() {
        return playedTime;
    }

    public void setPlayedTime(ZonedDateTime playedTime) {
        this.playedTime = playedTime;
    }
}

