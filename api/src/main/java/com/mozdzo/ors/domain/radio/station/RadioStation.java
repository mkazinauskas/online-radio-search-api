package com.mozdzo.ors.domain.radio.station;

import com.mozdzo.ors.domain.radio.station.genre.Genre;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;
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

    @Column(name = "title", length = 100, unique = true, nullable = false)
    private String title;

    @Column(name = "website", length = 100, unique = true)
    private String website;

    @ManyToMany(cascade = REFRESH, fetch = EAGER)
    @JoinTable(
            name = "genres_to_radio_stations",
            joinColumns = {@JoinColumn(name = "radio_station_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private Set<Genre> genres = new HashSet<>();

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

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }
}
