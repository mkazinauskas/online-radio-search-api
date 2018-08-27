package com.mozdzo.ors.domain.radio.station;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "radio_stations")
public class RadioStation {
    @Id
    @GeneratedValue(generator = "radio_stations_sequence", strategy = SEQUENCE)
    @SequenceGenerator(name = "radio_stations_sequence", sequenceName = "radio_stations_sequence", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE", length = 100)
    private String title;

    RadioStation() {
    }

    public RadioStation(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
