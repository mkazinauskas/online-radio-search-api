package com.modzo.ors.stations.domain.radio.station;

import com.modzo.ors.stations.domain.radio.station.genre.Genre;
import com.modzo.ors.stations.domain.radio.station.song.RadioStationSong;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Entity
@Table(name = "radio_stations")
public class RadioStation {
    @Id
    @GeneratedValue(generator = "radio_stations_sequence", strategy = SEQUENCE)
    @SequenceGenerator(name = "radio_stations_sequence", sequenceName = "radio_stations_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_id", length = 20, unique = true, nullable = false)
    private String uniqueId = randomAlphanumeric(20);

    @Column(name = "created", nullable = false)
    private ZonedDateTime created = ZonedDateTime.now();

    @Column(name = "title", length = 100, unique = true, nullable = false)
    private String title;

    @Column(name = "website", length = 100, unique = true)
    private String website;

    @Column(name = "enabled")
    private boolean enabled = true;

    @ManyToMany(cascade = REFRESH, fetch = EAGER)
    @JoinTable(
            name = "genres_to_radio_stations",
            joinColumns = {@JoinColumn(name = "radio_station_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(cascade = REFRESH, fetch = LAZY)
    @JoinColumn(name = "radio_station_id")
    private List<RadioStationSong> songs = new ArrayList<>();

    RadioStation() {
    }

    public RadioStation(String title) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public List<RadioStationSong> getSongs() {
        return songs;
    }

    public void setSongs(List<RadioStationSong> songs) {
        this.songs = songs;
    }
}
